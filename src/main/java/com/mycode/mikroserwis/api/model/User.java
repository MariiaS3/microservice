package com.mycode.mikroserwis.api.model;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.vertx.core.json.JsonObject;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    
    private UUID _id;
    private String login;
    private String password;

    public User(){
        
    }
    
    public User(JsonObject jsonObject) {
        this._id =UUID.fromString(jsonObject.getString("_id"));
        this.login = jsonObject.getString("login");
        this.password = jsonObject.getString("password");
    }


    public UUID get_id() {
        return _id;
    }
    public void set_id(UUID id) {
        this._id = id;
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
