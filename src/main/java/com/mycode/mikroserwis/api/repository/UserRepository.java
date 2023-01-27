package com.mycode.mikroserwis.api.repository;

import java.util.UUID;

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
        UUID uuid = UUID.randomUUID();
        user.set_id(uuid);
        Future<String> uFuture = mongoClient.insert("user", JsonObject.mapFrom(user)); 

        return uFuture.flatMap(result ->{
            JsonObject jsonObject = new JsonObject().put("_id", uuid.toString());
            User newUser = new User(jsonObject);
            return Future.succeededFuture(newUser);
        });
        
    }

}
