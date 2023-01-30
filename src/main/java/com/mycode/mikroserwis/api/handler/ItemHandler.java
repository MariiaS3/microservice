package com.mycode.mikroserwis.api.handler;

import java.util.UUID;
import io.vertx.core.json.JsonObject;
import com.mycode.mikroserwis.api.model.Item;
import com.mycode.mikroserwis.api.service.ItemService;
import com.mycode.mikroserwis.util.ResponseUtil;

import io.vertx.ext.web.RoutingContext;

public class ItemHandler {
    private ItemService itemService;

    public ItemHandler(ItemService userService) {
        this.itemService = userService;
    }
    
    // this function returns all items(used for tests)
    // public void getAllItems(RoutingContext rc){
    //     itemService.getAllItem()
    //     .onSuccess(success -> ResponseUtil.onSuccessResponse(rc, success))
    //     .onFailure(throwable -> ResponseUtil.onErrorResponse(rc, throwable));
    // }


    public void getItems(RoutingContext rc){
        String owner  = rc.pathParam("id");
        
        itemService.getItemByOwner(owner)
        .onSuccess(success -> ResponseUtil.onSuccessResponse(rc, success))
        .onFailure(throwable -> ResponseUtil.onErrorResponse(rc, throwable));
    }


   public void insertItems(RoutingContext rc, JsonObject jsonObject){
        String owner  = rc.pathParam("id");
        Item item = mapRequestBodyToItem(jsonObject);
        item.setOwner(UUID.fromString(owner));

        itemService.insertItem(item)
        .onSuccess(success -> ResponseUtil.onSuccessResponseAddItem(rc, success))
        .onFailure(throwable -> ResponseUtil.onErrorResponse(rc, throwable));
    }


    private Item mapRequestBodyToItem(JsonObject jsonObject){
        Item item = new Item();

        item = jsonObject.mapTo(Item.class);
        return item;
    }


    
}
