package com.mycode.mikroserwis.util;

import com.mycode.mikroserwis.api.model.User;

import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

public class ResponseUtil {
    

    public static void onSuccessResponse(RoutingContext rc,  Object ob){
        rc.response().putHeader("Content-Type", "application/json")
        .end(Json.encodePrettily(ob));
    }

    public static void onSuccessResponseRegister(RoutingContext rc,  Object ob){
        rc.response().setStatusCode(204).setStatusMessage("Registering successfull.");
        rc.response().end("Registering successfull.");
    }
    
    public static void onErrorResponseRegister(RoutingContext rc,  Object ob){
        rc.response().setStatusCode(404).setStatusMessage("User already exist");
        rc.response().end("User already exist");
    }

    public static void onSuccessResponseGeToken(RoutingContext rc,  String token, User ob){
        JsonObject jtoken = new JsonObject().put("token", token);
        rc.response().putHeader("Content-Type", "application/json").putHeader("Authorization",token)
        .end(Json.encodePrettily(jtoken));
    }
    public static void onErrorResponseLogin(RoutingContext rc,  Object ob){
        rc.response().setStatusCode(404).setStatusMessage("Wrong password or login");
        rc.response().end("Wrong password or login");
    }
    public static void onSuccessResponseAddItem(RoutingContext rc,  Object ob){
        rc.response().setStatusCode(204).setStatusMessage("Item created successfull.");
        rc.response().end("Item created successfull.");
    }

    public static void onErrorResponse(RoutingContext rc, Throwable er){
       
        rc.response().setStatusCode(404).setStatusMessage("Not found");
        rc.response().end("You have not provided an authentication token, the one provided has expired, was revoked or is not authentic.");
    }
}
