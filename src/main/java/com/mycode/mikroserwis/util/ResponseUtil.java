package com.mycode.mikroserwis.util;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class ResponseUtil {
    

    public static void onSuccessResponse(RoutingContext rc, int st, Object ob){
        rc.response().setStatusCode(st).putHeader("Content-Type", "application/json")
        .end(Json.encodePrettily(ob));
    }

    public static void onErrorResponse(RoutingContext rc, int st, Throwable throwable){
        JsonObject error = new JsonObject().put("error", throwable);

        rc.response().setStatusCode(st).putHeader("Content-Type", "application/json")
        .end(Json.encodePrettily(error));
    }
}
