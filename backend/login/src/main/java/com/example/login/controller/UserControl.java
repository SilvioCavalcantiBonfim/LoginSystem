package com.example.login.controller;

import com.example.login.entities.LoginRequest;
import com.example.login.entities.User;
import com.example.login.service.UserService;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.*;

@CrossOrigin
@RestController
public class UserControl {
    @Autowired
    private UserService userService;

    @PostMapping("/auth")
    public Map<String, Object> auth(@RequestBody LoginRequest loginRequest){
        Map<String, Object> rt = new LinkedHashMap<>();
        try{
            List<User> users = userService.findByEmail(loginRequest.getEmail());
            if(users.size() == 0)
                throw new IllegalIdentifierException(null);
            User user = users.get(0);
            if (!user.isPassowrd(loginRequest.getPassword()))
                throw new IllegalIdentifierException(null);
            rt.put("key", userService.GenerateKey(user));
        }catch (IllegalIdentifierException e){
            rt.put("error", "Invalid authentication.");
        }
        return rt;
    }

    @GetMapping("/profile")
    Map<String, Object> profile(@RequestBody Map<String,String> key){
        Map<String, Object> rt = new LinkedHashMap<>();
        try {
            if(!userService.keyIsValid(key.get("key")))
                throw new NullPointerException();
            List<User> profile = userService.findByEmail(userService.keyPart(key.get("key")).get(0));
            String[] name = {profile.get(0).getFirstname(), profile.get(0).getLastname()};
            rt.put("name", name);
            rt.put("email", profile.get(0).getEmail());
        }catch (NullPointerException e){
            rt.put("error", "Unexpected error.");
        }
        return rt;
    }
}
