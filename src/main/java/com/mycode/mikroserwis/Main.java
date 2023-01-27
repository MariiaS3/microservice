package com.mycode.mikroserwis;
import com.mycode.mikroserwis.verticle.MainVerticle;

import io.vertx.core.Vertx;

public class Main {

    static public  void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(MainVerticle.class.getName());
    }
    
}
