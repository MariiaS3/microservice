package com.mycode.mikroserwis.api.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mycode.mikroserwis.api.model.Item;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.*;



public class ItemRepositry {
    private final MongoClient mongoClient;

    public ItemRepositry(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    // public Future<List<Item>> getAllItem(){
        
    //     JsonObject jsonObject = new JsonObject();
        
    //     Future<List<JsonObject>> jFuture = mongoClient.find("item", jsonObject);
        
    //     return jFuture.flatMap(result ->{
    //         List<Item> items = new ArrayList<>();
    //         result.forEach((item) -> {
    //             items.add(new Item(item));
    //         });
    //         return Future.succeededFuture(items);
    //     });
    // }
    
    public Future<List<Item>> getItemByOwner(String owner){
        JsonObject jsonObject = new JsonObject().put("owner", owner);

       return mongoClient.find("item", jsonObject).flatMap(result ->{
        try{
            if(result == null){
                return Future.failedFuture("Not found");
            }else{
                List<Item> items = new ArrayList<>();
                result.forEach(item -> {
                    items.add(new Item(item));
                });
                return Future.succeededFuture(items);
            }
        }catch(Exception ex){
            return Future.failedFuture(ex);
       }
    });
    }

    public Future<Item> insertItem(Item item){
        UUID uuid = UUID.randomUUID();
        item.set_id(uuid);
        Future<String> iFuture = mongoClient.insert("item", new JsonObject().mapFrom(item));
        return iFuture.flatMap(result ->{
            Item newItem = item;
            return Future.succeededFuture(newItem);
        });
    }
}
