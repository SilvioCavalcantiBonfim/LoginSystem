package com.example.login.controller;

import com.example.login.entities.User;
import com.example.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class UserControl {
    @Autowired
    private UserService userService;

    @GetMapping("/user/all")
    public List<User> find(){
        return (List<User>) userService.findAll();
    }
}
