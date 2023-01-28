package com.mycode.mikroserwis.api.repository;

import java.util.Base64;
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

    public Future<User> getUserByLogin(String login, String password) {
        JsonObject jsonObject = new JsonObject().put("login", login);
        
        return mongoClient.findOne("user", jsonObject, null).flatMap(res ->{
            try{
                if(res == null){
                    return Future.failedFuture("Not found");
                }else{
                    byte[] decodedBytes = Base64.getDecoder().decode(res.getString("password"));
                    String resPassword = new String(decodedBytes);
                    if(resPassword == password){
                        User user = new User(res);
                        return Future.succeededFuture(user);
                    }else{
                        return Future.failedFuture("Wrong password");
                    }

                }
            }catch(Exception ex){
                return Future.failedFuture(ex);
           }
        });
    }

    public Future<User> insertUser(User user){

        UUID uuid = UUID.randomUUID();
        user.set_id(uuid);
        JsonObject jsonObject = new JsonObject().put("login", user.getLogin());
        
        return mongoClient.findOne("user", jsonObject, null).flatMap(res ->{
                if(res == null){
                    return  mongoClient.insert("user", JsonObject.mapFrom(user)).flatMap(result ->{
                        JsonObject jObject = new JsonObject().put("_id", uuid.toString());
                        User newUser = new User(jObject);
                        return Future.succeededFuture(newUser);
                    }); 
                }else{
                    return Future.failedFuture("User already exist");
                }
        });
       
        
    }

}
