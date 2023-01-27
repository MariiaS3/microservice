package com.mycode.mikroserwis.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mycode.mikroserwis.api.model.Item;
import com.mycode.mikroserwis.api.repository.ItemRepositry;
import com.mycode.mikroserwis.api.service.ItemService;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({MockitoExtension.class, VertxExtension.class})
public class ItemServiceTest {
    
    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepositry itemRepositry;

    @Test
    void shouldReturnAllItemsWhenGetItemsCalled(Vertx vertx, VertxTestContext testContext){
        itemService = new ItemService(itemRepositry);

        List<Item> items = new ArrayList<>();
        Item item = getItem();
        Future<List<Item>> future = Future.succeededFuture(items);
        items.add(item);

        when(itemRepositry.getAllItem()).thenReturn(future);

        itemService.getAllItem().flatMap(result ->{
            testContext.verify(() ->{
                assertThat(1).isEqualTo(result.size());
                assertThat( result.get(0))
                    .hasFieldOrPropertyWithValue("_id", UUID.fromString("6219b1a3-2499-546d-a687-cca010a49894"))
                    .hasFieldOrPropertyWithValue("owner", UUID.fromString("6210b1a3-2499-446d-a687-cce010a49864"))
                    .hasFieldOrPropertyWithValue("name", "My item");
                    
            });
            testContext.completeNow();
            return Future.succeededFuture(result);
        });
    }

    @Test
    void shouldReturnItemsByOwnerIdWhenGetItemByOwnerCalled(Vertx vertx, VertxTestContext testContext){
        itemService = new ItemService(itemRepositry);

        List<Item> items = new ArrayList<>();
        Item item = getItem();
        Future<List<Item>> future = Future.succeededFuture(items);
        items.add(item);
                                                                                                                                                                                                                                                                    
        when(itemRepositry.getItemByOwner(any())).thenReturn(future);

        itemService.getItemByOwner(any()).flatMap(result ->{
            testContext.verify(() ->{
                assertThat(1).isEqualTo(result.size());
            });
            testContext.completeNow();
            return Future.succeededFuture(result);
        });
    }

    private Item getItem(){
        JsonObject jObject = new JsonObject()
        .put("_id", "6219b1a3-2499-546d-a687-cca010a49894")
        .put("owner", "6210b1a3-2499-446d-a687-cce010a49864")
        .put("name", "My item");
        return new Item(jObject);
    }
}
