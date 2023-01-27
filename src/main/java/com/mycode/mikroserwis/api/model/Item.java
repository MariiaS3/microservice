package com.mycode.mikroserwis.api.model;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.vertx.core.json.JsonObject;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item {

    private UUID id;
    private UUID owner;
    private String name;


    public Item() {

    }
    public Item(JsonObject jsonObject) {
        this.id =UUID.fromString(jsonObject.getString("id"));
        this.owner =UUID.fromString(jsonObject.getString("owner"));
        this.name = jsonObject.getString("name");
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
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
