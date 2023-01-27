package com.mycode.mikroserwis.verticle;

import com.mycode.mikroserwis.api.handler.ItemHandler;
import com.mycode.mikroserwis.api.handler.UserHandler;

import com.mycode.mikroserwis.api.repository.ItemRepositry;
import com.mycode.mikroserwis.api.repository.UserRepository;
import com.mycode.mikroserwis.api.router.ApiRouter;
import com.mycode.mikroserwis.api.service.ItemService;
import com.mycode.mikroserwis.api.service.UserService;

import io.vertx.core.AbstractVerticle;

import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.*;


public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    JsonObject config = new JsonObject()
        .put("connection_string", "mongodb://localhost:27017")
        .put("db_name", "microservis")
        .put("username", "root")
        .put("password", "password")
        .put("authSource", "admin");;


    final MongoClient mongoClient = MongoClient.createShared(vertx, config);

    final ItemRepositry itemRepositry = new ItemRepositry(mongoClient);
    final UserRepository userRepository = new UserRepository(mongoClient);
    final ItemService itemService = new ItemService(itemRepositry);
    final UserService userService = new UserService(userRepository);
    final ItemHandler itemHandler = new ItemHandler(itemService);
    final UserHandler userHandler = new UserHandler(userService);
    final ApiRouter apiRouter = new ApiRouter(vertx, itemHandler, userHandler);
    
    vertx.createHttpServer()
        .requestHandler(apiRouter.getRouter())
        .listen(8080, http -> {
          if (http.succeeded()) {
            startPromise.complete();
            System.out.println("HTTP server started on port 8080");
          } else {
            startPromise.fail(http.cause());
          }
        });

  }

}
