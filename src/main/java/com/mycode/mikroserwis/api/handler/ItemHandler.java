package com.mycode.mikroserwis.api.handler;

import java.util.UUID;

import com.mycode.mikroserwis.api.model.Item;
import com.mycode.mikroserwis.api.service.ItemService;
import com.mycode.mikroserwis.util.ResponseUtil;

import io.vertx.ext.web.RoutingContext;

public class ItemHandler {
    private ItemService itemService;

    public ItemHandler(ItemService userService) {
        this.itemService = userService;
    }
    

    public void getAllItems(RoutingContext rc){
        itemService.getAllItem()
        .onSuccess(success -> ResponseUtil.onSuccessResponse(rc, success))
        .onFailure(throwable -> ResponseUtil.onErrorResponse(rc, throwable));
    }


    public void getItems(RoutingContext rc){
        String owner  = rc.pathParam("id");
        
        itemService.getItemByOwner(owner)
        .onSuccess(success -> ResponseUtil.onSuccessResponse(rc, success))
        .onFailure(throwable -> ResponseUtil.onErrorResponse(rc, throwable));
    }


    public void insertItems(RoutingContext rc){
        String owner  = rc.pathParam("id");
        Item item = mapRequestBodyToItem(rc);
        item.setOwner(UUID.fromString(owner));

        itemService.insertItem(item)
        .onSuccess(success -> ResponseUtil.onSuccessResponseAddItem(rc, success))
        .onFailure(throwable -> ResponseUtil.onErrorResponse(rc, throwable));
    }

    private Item mapRequestBodyToItem(RoutingContext rc){
        Item item = new Item();

        try{
            item = rc.getBodyAsJson().mapTo(Item.class);
        }catch(IllegalArgumentException ex){
            ResponseUtil.onErrorResponse(rc, ex);
        }

        return item;
    }

    
}
