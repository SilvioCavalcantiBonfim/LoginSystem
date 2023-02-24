package com.example.login.service;

import com.example.login.entities.User;

import java.util.IllegalFormatException;
import java.util.List;

public interface UserServiceInterface {
    List<User> findAll();

    boolean keyIsValid(String key) throws IllegalFormatException;
//
    void save(User user);
    String GenerateKey(User user);

    List<String> keyPart(String key);

//    void ResetCode(String key);
}
