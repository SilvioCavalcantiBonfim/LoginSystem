package com.example.login.service;

import com.example.login.entities.User;

import java.util.IllegalFormatException;
import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {

  List<User> findByEmail(String email);
    Optional<User> findById(Long id);
    void save(User user);

}
