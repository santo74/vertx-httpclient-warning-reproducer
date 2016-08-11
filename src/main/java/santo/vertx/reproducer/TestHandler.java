/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santo.vertx.reproducer;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpClient;
import io.vertx.ext.web.RoutingContext;

/**
 *
 * @author Guy De Schepper
 */
public interface TestHandler extends Handler<RoutingContext> {
    static TestHandler create(WebService service, HttpClient http) {
        return new TestHandlerImpl(service, http);
    }
}
