package com.example.login.service;

import com.example.login.entities.User;
import com.example.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServiceInterface{
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll(){
        return (List<User>) userRepository.findAll();
    }
}
