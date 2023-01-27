package com.mycode.mikroserwis.api.repository;

import java.util.ArrayList;
import java.util.List;

import com.mycode.mikroserwis.api.model.Item;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.*;



public class ItemRepositry {
    private final MongoClient mongoClient;

    public ItemRepositry(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public Future<List<Item>> getAllItem(){
        
        System.out.println("------------------repository----------------------------");
        JsonObject jsonObject = new JsonObject();

        Future<List<JsonObject>> jFuture = mongoClient.find("item", jsonObject);
       
        return jFuture.flatMap(result ->{
            List<Item> items = new ArrayList<>();
            result.forEach((item) -> {
                items.add(new Item(item));
            });
            return Future.succeededFuture(items);
        });
    }

    public Future<List<Item>> getItemByOwner(String owner){
        JsonObject jsonObject = new JsonObject().put("owner", owner);

        Future<List<JsonObject>> jFuture = mongoClient.find("item", jsonObject);

        return jFuture.flatMap(result ->{
            List<Item> items = new ArrayList<>();
            result.forEach(item -> items.add(new Item(item)));

            return Future.succeededFuture(items);
        });
    }

    public Future<Item> insertItem(Item item){
        Future<String> iFuture = mongoClient.insert("item", new JsonObject().mapFrom(item));
        return iFuture.flatMap(result ->{
            JsonObject jsonObject = new JsonObject().put("id", result);
            Item newItem = new Item(jsonObject);

            return Future.succeededFuture(newItem);
        });
    }
}
