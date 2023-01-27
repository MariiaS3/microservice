package com.mycode.mikroserwis.api.model;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.vertx.core.json.JsonObject;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    
    private UUID id;
    private String login;
    private String password;

    public User(){
        
    }
    
    public User(JsonObject jsonObject) {
        this.id =UUID.fromString(jsonObject.getString("id"));
        this.login = jsonObject.getString("login");
        this.password = jsonObject.getString("password");
    }


    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    
}
