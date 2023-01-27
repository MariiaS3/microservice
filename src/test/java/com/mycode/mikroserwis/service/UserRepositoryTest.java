package com.mycode.mikroserwis.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mycode.mikroserwis.api.model.User;
import com.mycode.mikroserwis.api.repository.UserRepository;
import com.mycode.mikroserwis.api.service.UserService;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;


import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({MockitoExtension.class, VertxExtension.class})
public class UserRepositoryTest {
    
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;



    @Test
    void shouldReturnUserWhenEmailIsExist(Vertx vertx, VertxTestContext testContext){
        userService = new UserService(userRepository);
        User user = getUser();
        Future<User> future = Future.succeededFuture(user);

        when(userRepository.getUserByLogin(any())).thenReturn(future);

        userService.getUserByLogin("user@domain.com").flatMap(result ->{
            testContext.verify(() ->{
                assertThat(result).isNotNull();
                assertThat( result.getLogin()).isEqualTo("user@domain.com");
                    
            });
            testContext.completeNow();
            return Future.succeededFuture(result);
        });
    }


    private User getUser(){
        JsonObject jObject = new JsonObject()
        .put("_id", "6210b1a3-2499-446d-a687-cce010a49864")
        .put("login", "user@domain.com")
        .put("password", "SomePassword1");
        return new User(jObject);
    }
}
