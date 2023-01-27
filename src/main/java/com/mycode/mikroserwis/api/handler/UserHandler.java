package com.mycode.mikroserwis.api.handler;

import com.mycode.mikroserwis.api.model.User;
import com.mycode.mikroserwis.api.service.UserService;
import com.mycode.mikroserwis.util.ResponseUtil;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.RoutingContext;


public class UserHandler {
    
    private UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }
    
    
    public void login(RoutingContext rc,JsonObject jsonObject, JWTAuth jwt){
            userService.getUserByLogin(jsonObject.getString("login"))
            .onSuccess(success -> ResponseUtil.onSuccessResponseGeToken(rc, jwt, success))
            .onFailure(throwable -> ResponseUtil.onErrorResponse(rc, throwable));

    }
    public void register(RoutingContext rc,JsonObject jsonObject, JWTAuth jwt){    
        User user = mapRequestBodyToUser(jsonObject, rc);

            userService.insertUser(user)
            .onSuccess(success -> ResponseUtil.onSuccessResponseRegister(rc,  success))
            .onFailure(throwable -> ResponseUtil.onErrorResponse(rc, throwable));

    }

    private User mapRequestBodyToUser(JsonObject jsonObject, RoutingContext rc){
        User user = new User();

        try{
            user =jsonObject.mapTo(User.class);
        }catch(IllegalArgumentException ex){
            ResponseUtil.onErrorResponse(rc, ex);
        }

        return user;
    }
}
