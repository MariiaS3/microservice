package com.mycode.mikroserwis.api.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mycode.mikroserwis.api.model.Item;
import com.mycode.mikroserwis.api.service.ItemService;
import com.mycode.mikroserwis.util.ResponseUtil;

import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

public class ItemHandler {
    private ItemService itemService;

    public ItemHandler(ItemService userService) {
        this.itemService = userService;
    }
    

    public void getAllItems(RoutingContext rc){
        itemService.getAllItem()
        .onSuccess(success -> ResponseUtil.onSuccessResponse(rc,204, success))
        .onFailure(throwable -> ResponseUtil.onErrorResponse(rc,401, throwable));
        // .flatMap(result ->{
        //     List<Item> items = new ArrayList<>();
        //     items.addAll(result);
        //     rc.response().setStatusCode(204).putHeader("Content-Type", "application/json").putHeader("Connection", "keep-alive")
        //     .end(Json.encodePrettily(items));

        //     return Future.succeededFuture(items);
        // });
    }


    public void getItems(RoutingContext rc){
        String owner  = rc.pathParam("owner");
        
        itemService.getItemByOwner(owner)
        .flatMap(result ->{
            List<Item> items = new ArrayList<>();
            items.addAll(result);
            rc.response().setStatusCode(204).putHeader("Content-Type", "application/json")
            .end(Json.encodePrettily(items));

            return Future.succeededFuture(items);
        });
    }


    public void insertItems(RoutingContext rc){
        String owner  = rc.pathParam("owner");
        Item item = mapRequestBodyToItem(rc);
        item.setOwner(UUID.fromString(owner));

        itemService.insertItem(item)
        .onSuccess(success -> ResponseUtil.onSuccessResponse(rc,204, success))
        .onFailure(throwable -> ResponseUtil.onErrorResponse(rc,401, throwable));
    }

    private Item mapRequestBodyToItem(RoutingContext rc){
        Item item = new Item();

        try{
            item = rc.getBodyAsJson().mapTo(Item.class);
        }catch(IllegalArgumentException ex){
            ResponseUtil.onErrorResponse(rc,401, ex);
        }

        return item;
    }

    
}
