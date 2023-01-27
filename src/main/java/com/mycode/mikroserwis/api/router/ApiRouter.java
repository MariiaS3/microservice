package com.mycode.mikroserwis.api.router;

import com.mycode.mikroserwis.api.handler.ItemHandler;
import com.mycode.mikroserwis.api.handler.UserHandler;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class ApiRouter {

    private Vertx vertx;
    private ItemHandler itemHandler;
    private UserHandler userHandler;
    


    public ApiRouter(Vertx vertx, UserHandler userHandler) {
        this.vertx = vertx;
        this.userHandler = userHandler;
    }

    public ApiRouter(Vertx vertx, ItemHandler itemHandler) {
        this.vertx = vertx;
        this.itemHandler = itemHandler;
    }

    public ApiRouter(Vertx vertx, ItemHandler itemHandler, UserHandler userHandler) {
        this.vertx = vertx;
        this.itemHandler = itemHandler;
        this.userHandler = userHandler;
    }

    public Router getRouter(){

        final Router apiRouter = Router.router(vertx);

        apiRouter.route("/api/v1/items").handler(BodyHandler.create());
        apiRouter.get("/api/v1/items").handler(itemHandler::getAllItems);
        apiRouter.get("/api/v1/items/:owner").handler(itemHandler::getItems);
        apiRouter.post("/api/v1/items/:owner").handler(itemHandler::insertItems);


        return apiRouter;

    }

}
