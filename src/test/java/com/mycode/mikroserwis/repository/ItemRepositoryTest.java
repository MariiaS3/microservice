package com.mycode.mikroserwis.repository;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.mycode.mikroserwis.api.model.Item;
import com.mycode.mikroserwis.api.repository.ItemRepositry;

import io.vertx.ext.mongo.*;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;


@ExtendWith(VertxExtension.class)
public class ItemRepositoryTest {

    JsonObject config;
    ItemRepositry itemRepositry;

    @BeforeEach
    void init(){
        config = new JsonObject()
        .put("connection_string", "mongodb://localhost:27017")
        .put("db_name", "microservis")
        .put("username", "root")
        .put("password", "password")
        .put("authSource", "admin");;
    }

    @Test
    void shouldReturnAllItems(Vertx vertx, VertxTestContext testContext){
        MongoClient mongoClient = MongoClient.createShared(vertx, config);
        itemRepositry = new ItemRepositry(mongoClient);

        itemRepositry.getAllItem().flatMap(result ->{
            testContext.verify(() ->{
                Assertions.assertEquals(result.size(), 2);
            });
            testContext.completeNow();
            return Future.succeededFuture(result);
        });
    }

    @Test
    void shouldReturnItemsByOwnerId(Vertx vertx, VertxTestContext testContext){
        MongoClient mongoClient = MongoClient.createShared(vertx, config);
        itemRepositry = new ItemRepositry(mongoClient);

        itemRepositry.getItemByOwner("6210b1a3-2499-446d-a687-cce010a49864").flatMap(result ->{
            testContext.verify(() ->{
                Assertions.assertAll(
                    () -> Assertions.assertEquals(result.size(), 1),
                    () -> Assertions.assertEquals(result.get(0).getName(), "My item")
                    
                );
            });
            testContext.completeNow();
            return Future.succeededFuture(result);
        });
    }
    
    @Test
    void shouldCallOnSuccessWenCreateNewItm(Vertx vertx, VertxTestContext testContext){
        MongoClient mongoClient = MongoClient.createShared(vertx, config);
        itemRepositry = new ItemRepositry(mongoClient);
        
        Item item = new Item();
        item.setName("Test item");
        item.setOwner(UUID.randomUUID());
        item.set_id(UUID.randomUUID());
    
        itemRepositry.insertItem(item)
        .onSuccess( s -> {
            testContext.verify(() ->{
                Assertions.assertEquals(s.getName(), "Test item");
            });
            testContext.completeNow();
        });
           
    }
}
