package com.mycode.mikroserwis.api.handler;

import com.mycode.mikroserwis.api.service.UserService;


public class UserHandler {
    
    private UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    
}
