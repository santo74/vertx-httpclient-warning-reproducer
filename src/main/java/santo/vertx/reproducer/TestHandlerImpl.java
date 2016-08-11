/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santo.vertx.reproducer;

import com.englishtown.promises.Deferred;
import com.englishtown.promises.Promise;
import com.englishtown.promises.When;
import com.englishtown.promises.WhenFactory;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;

/**
 *
 * @author Guy De Schepper
 */
public class TestHandlerImpl implements TestHandler {
    private final WebService service;
    private final HttpClient http;

    public TestHandlerImpl(WebService service, HttpClient http) {
        this.service = service;
        this.http = http;
    }
	
    @Override
    public void handle(RoutingContext ctx) {
        When when = WhenFactory.createAsync();
        Deferred<JsonObject> d = when.defer();
        Promise<JsonObject> auth = d.getPromise();
        auth.then(authResponse -> {
            for (FileUpload f : ctx.fileUploads()) {
                service.getVertx().fileSystem().readFile(f.uploadedFileName(), readfile -> {
                    if (readfile.succeeded()) {
                        Buffer fileContent = readfile.result();

                        HttpClientRequest httpRequest = http.put("/remote", response -> {
                            int statusCode = response.statusCode();
                            System.out.println("Status code: " + statusCode);
                            
                            JsonObject uploadResponse = new JsonObject();
                            uploadResponse.put("statuscode", statusCode);
                            ctx.response()
                                .putHeader("content-type", "application/json; charset=utf-8")
                                .setStatusCode(200).end(uploadResponse.encode());
                        });
                        httpRequest.end(fileContent);
                    }
                    else {
                        System.err.println("Error loading file: " + readfile.cause());
                    }
                });
            }
            
            return null;
        });
        d.resolve(new JsonObject());
    }
    
}
