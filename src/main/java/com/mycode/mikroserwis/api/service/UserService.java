package com.mycode.mikroserwis.api.service;

import com.mycode.mikroserwis.api.model.User;
import com.mycode.mikroserwis.api.repository.UserRepository;

import io.vertx.core.Future;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Future<User> getUserByLogin(String login){
        return userRepository.getUserByLogin(login);
    }

    public Future<User> insertUser(User user){
        return userRepository.insertUser(user);
    }
}
