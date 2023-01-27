package com.mycode.mikroserwis.api.model;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.vertx.core.json.JsonObject;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item {

    private UUID _id;
    private UUID owner;
    private String name;


    public Item() {

    }
    public Item(JsonObject jsonObject) {
        this._id =UUID.fromString(jsonObject.getString("_id"));
        this.owner =UUID.fromString(jsonObject.getString("owner"));
        this.name = jsonObject.getString("name");
    }

    public UUID get_id() {
        return _id;
    }
    public void set_id(UUID id) {
        this._id = id;
    }
    public UUID getOwner() {
        return owner;
    }
    public void setOwner(UUID owner) {
        this.owner = owner;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    
    

}
