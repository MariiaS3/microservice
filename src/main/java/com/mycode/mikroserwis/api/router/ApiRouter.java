package com.mycode.mikroserwis.api.router;

import com.mycode.mikroserwis.api.handler.ItemHandler;
import com.mycode.mikroserwis.api.handler.UserHandler;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.KeyStoreOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.JWTAuthHandler;


public class ApiRouter {

    private Vertx vertx;
    private ItemHandler itemHandler;
    private UserHandler userHandler;
    
    public ApiRouter(Vertx vertx, ItemHandler itemHandler, UserHandler userHandler) {
        this.vertx = vertx;
        this.itemHandler = itemHandler;
        this.userHandler = userHandler;
    }

    public Router getRouter(){

        final Router apiRouter = Router.router(vertx);

        JWTAuthOptions authConfig = new JWTAuthOptions().setKeyStore(new KeyStoreOptions()
        .setPath("./keystore.jceks").setPassword("secret"));
        JWTAuth jwt= JWTAuth.create(vertx, authConfig);

        apiRouter.route("/api").handler(BodyHandler.create());
        apiRouter.post("/api/login").handler(ctx -> {
            ctx.request().bodyHandler(body -> {
                JsonObject jsonObject = new JsonObject(body.toString());
                login(ctx,jsonObject, jwt);
            });
        });
        apiRouter.post("/api/register").handler(ctx->{
            ctx.request().bodyHandler(body -> {
                JsonObject jsonObject = new JsonObject(body.toString());
                register(ctx, jsonObject, jwt);
            });
        });

        apiRouter.route("/api*").handler(JWTAuthHandler.create(jwt, "/api/login"));

        // this is the endpoint that returns all items(used for tests)
        // apiRouter.get("/api/items").handler(itemHandler::getAllItems);
        apiRouter.get("/api/items/:id").handler(itemHandler::getItems);
        apiRouter.post("/api/items/:id").handler(ctx->{
            ctx.request().bodyHandler(body -> {
                JsonObject jsonObject = new JsonObject(body.toString());
                itemHandler.insertItems(ctx, jsonObject);
            });
        });


        return apiRouter;

    }

    public void login(RoutingContext rc,JsonObject jsonObject, JWTAuth jwt){
        userHandler.login(rc,jsonObject, jwt);
    }

    public void register(RoutingContext rc,JsonObject jsonObject, JWTAuth jwt){
        userHandler.register(rc,jsonObject, jwt);
    }
   
}
