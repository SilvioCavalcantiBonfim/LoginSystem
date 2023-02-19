package com.example.login.service;

import com.example.login.entities.User;

import java.util.List;

public interface UserServiceInterface {
    List<User> findAll();
}
