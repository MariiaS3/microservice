package com.mycode.mikroserwis.api.service;

import java.util.List;

import com.mycode.mikroserwis.api.model.Item;
import com.mycode.mikroserwis.api.repository.ItemRepositry;

import io.vertx.core.Future;

public class ItemService {

    private ItemRepositry itemRepositry;

    public ItemService(ItemRepositry itemRepositry) {
        this.itemRepositry = itemRepositry;
    }

    public Future<List<Item>> getAllItem(){
        System.out.println("------------------service----------------------------");
        return itemRepositry.getAllItem();
    }

    public Future<List<Item>> getItemByOwner(String owner){
        return itemRepositry.getItemByOwner(owner);
    }

    public Future<Item> insertItem(Item item){
        return itemRepositry.insertItem(item);
    }
}
