package com.example.login.controller;

import com.example.login.entities.LoginRequest;
import com.example.login.entities.UpdateProfile;
import com.example.login.entities.User;
import com.example.login.service.UserService;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Array;
import java.util.*;

@CrossOrigin
@RestController
public class UserControl {
    @Autowired
    private UserService userService;

    @PostMapping("/auth")
    public Map<String, Object> auth(@RequestBody LoginRequest loginRequest){
        try{
            Map<String, Object> rt = new LinkedHashMap<>();
            List<User> users = userService.findByEmail(loginRequest.getEmail());
            Exception ex = new Exception();
            if(users.size() == 0)
                throw ex;
            User user = users.get(0);
            if (!user.isPassowrd(loginRequest.getPassword()))
                throw ex;
            rt.put("key", userService.GenerateKey(user));
            return rt;
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Authentication failure",e);
        }
    }

    @PutMapping("/profile/update")
    public Map<String, Object> update(@RequestBody UpdateProfile updateProfile){
        try{
            if(!userService.keyIsValid(updateProfile.getKey()))
                throw new NullPointerException();
            List<String> keypart = userService.keyPart(updateProfile.getKey());
            User profile = userService.findByEmail(keypart.get(0)).get(0);
            if(!updateProfile.getFirstname().equals("") && !updateProfile.getFirstname().equals(profile.getFirstname()))
                profile.setFirstname(updateProfile.getFirstname());
            if(!updateProfile.getLastname().equals("") && !updateProfile.getLastname().equals(profile.getLastname()))
                profile.setLastname(updateProfile.getLastname());
            userService.save(profile);
            return profile.returnUser();
        }catch (NullPointerException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Authentication failure",e);
        }
    }
    @GetMapping("/profile")
    public Map<String, Object> profile(@RequestHeader("key") String key){
        try {
            if(!userService.keyIsValid(key))
                throw new NullPointerException();
            List<User> profile = userService.findByEmail(userService.keyPart(key).get(0));
            return profile.get(0).returnUser();
        }catch (NullPointerException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Authentication failure",e);
        }
    }
}
