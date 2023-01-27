package com.mycode.mikroserwis.util;

import com.mycode.mikroserwis.api.model.User;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.RoutingContext;

public class ResponseUtil {
    

    public static void onSuccessResponse(RoutingContext rc,  Object ob){
        rc.response().putHeader("Content-Type", "application/json")
        .end(Json.encodePrettily(ob));
    }

    public static void onSuccessResponseRegister(RoutingContext rc,  Object ob){
        rc.response().setStatusCode(204).setStatusMessage("Registering successfull.");
    }
    public static void onSuccessResponseGeToken(RoutingContext rc,  JWTAuth jwtAuth, User ob){
        String token ="Bearer " + jwtAuth.generateToken(new JsonObject().put("username", ob.getLogin()),
        new JWTOptions().setExpiresInMinutes(3600));
        rc.response().putHeader("Content-Type", "application/json").putHeader("Authorization",token)
        .end(token);
    }

    public static void onSuccessResponseAddItem(RoutingContext rc,  Object ob){
        rc.response().setStatusCode(204).setStatusMessage("Item created successfull.");
    }

    public static void onErrorResponse(RoutingContext rc, Throwable throwable){
        rc.response().setStatusCode(401).setStatusMessage("You have not provided an authentication token, the one provided has expired, was revoked or is not authentic.");
    }
}
