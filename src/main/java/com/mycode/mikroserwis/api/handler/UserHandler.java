package com.mycode.mikroserwis.api.handler;

import java.util.Base64;


import com.mycode.mikroserwis.api.model.User;
import com.mycode.mikroserwis.api.service.UserService;
import com.mycode.mikroserwis.util.ResponseUtil;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.RoutingContext;

public class UserHandler {

    private UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public void login(RoutingContext rc, JsonObject jsonObject, JWTAuth jwt) {

        userService.getUserByLogin(jsonObject.getString("login"), jsonObject.getString("password"))
                .onSuccess(success -> {
                    String token = "Bearer " + jwt.generateToken(new JsonObject().put("username", success.getLogin()),
                            new JWTOptions().setExpiresInMinutes(3600));
                    ResponseUtil.onSuccessResponseGeToken(rc, token, success);
                })
                .onFailure(throwable -> ResponseUtil.onErrorResponseLogin(rc, throwable));

    }

    public void register(RoutingContext rc, JsonObject jsonObject, JWTAuth jwt) {
        User user = mapRequestBodyToUser(jsonObject, rc);

        userService.insertUser(user)
                .onSuccess(success -> ResponseUtil.onSuccessResponseRegister(rc, success))
                .onFailure(throwable -> ResponseUtil.onErrorResponseRegister(rc, throwable));

    }

    private User mapRequestBodyToUser(JsonObject jsonObject, RoutingContext rc) {
        User user = new User();

            user = jsonObject.mapTo(User.class);
            user.setPassword(Base64.getEncoder().encodeToString(user.getPassword().getBytes()));
        return user;
    }
}
