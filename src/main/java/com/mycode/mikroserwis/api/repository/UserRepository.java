package com.mycode.mikroserwis.api.repository;

import java.util.ArrayList;
import java.util.List;

import com.mycode.mikroserwis.api.model.User;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.*;

public class UserRepository {
    private final MongoClient mongoClient;

    public UserRepository(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public Future<User> getUserByLogin(String login){
        JsonObject jsonObject = new JsonObject().put("login", login);
        
        Future<JsonObject> jFuture = mongoClient.findOne("user", jsonObject, null);
        return  jFuture.flatMap(result -> {
            User user = new User(result);
            return Future.succeededFuture(user);
        });
    }

    public Future<User> insertUser(User user){
        Future<String> uFuture = mongoClient.insert("user", JsonObject.mapFrom(user)); 

        return uFuture.flatMap(result ->{
            JsonObject jsonObject = new JsonObject().put("id", result);
            User newUser = new User(jsonObject);

            return Future.succeededFuture(newUser);
        });
        
    }

}
