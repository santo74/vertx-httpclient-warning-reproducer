/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santo.vertx.reproducer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

/**
 *
 * @author Guy De Schepper
 */
public class WebService extends AbstractVerticle {
    
    @Override
    public void start() {
        System.out.println("Starting WebService");
        Router router = Router.router(vertx);

        // Add body handler
        router.route().handler(BodyHandler.create().setBodyLimit(10 * 1024 * 1024));

        HttpClientOptions options = new HttpClientOptions();
        options.setConnectTimeout(7000);
        options.setDefaultHost("internal.objectstore.eu");
        options.setDefaultPort(443);
        options.setSsl(true);
        options.setTrustAll(true);
        HttpClient http = vertx.createHttpClient(options);

        TestHandler handler = TestHandler.create(this, http);        
        router.route("/api/test").handler(handler);
        
        vertx.createHttpServer().requestHandler(router::accept).listen(7000);
        System.out.println("WebService listening on port 7000");
    }

    
}
