package com.example.login.controller;

import com.example.login.entities.Key;
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
            rt.put("key", user.GenerateKey().Encode());
            return rt;
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Authentication failure");
        }
    }
    @GetMapping("/profile")
    public Map<String, Object> profile(@RequestHeader("key") String key){
        try {
            Key currentKey = new Key(key);
            User profile = userService.findById(currentKey.getId()).get();
            if(!profile.YourKey(currentKey))
                throw new IllegalIdentifierException("");
            return profile.returnUser();
        }catch (NullPointerException | NumberFormatException | IllegalIdentifierException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Authentication failure",e);
        }
    }

    @PutMapping("/profile/update/password")
    public Map<String, Object> update(@RequestBody Map<String,String> updateProfile){
        try {
            Key currentKey = new Key(updateProfile.get("key"));
            User profile = userService.findById(currentKey.getId()).get();
            if(!profile.YourKey(currentKey))
                throw new IllegalIdentifierException("");
            profile.setPassword(updateProfile.get("password"));
            userService.save(profile);
            return profile.returnUser();
        }catch (NullPointerException | NumberFormatException | IllegalIdentifierException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Authentication failure",e);
        }
    }
    @PutMapping("/profile/update")
    public Map<String, Object> update(@RequestBody UpdateProfile updateProfile){
        try {
            System.out.println(updateProfile.toString());
            Key currentKey = new Key(updateProfile.getKey());
            User profile = userService.findById(currentKey.getId()).get();
            if(!profile.YourKey(currentKey))
                throw new IllegalIdentifierException("");
            if(!updateProfile.getFirstname().equals("") && !updateProfile.getFirstname().equals(profile.getFirstname()) && updateProfile.getFirstname() != null)
                profile.setFirstname(updateProfile.getFirstname());
            if(!updateProfile.getLastname().equals("") && !updateProfile.getLastname().equals(profile.getLastname()) && updateProfile.getLastname() != null)
                profile.setLastname(updateProfile.getLastname());
            if(!updateProfile.getEmail().equals("") && !updateProfile.getEmail().equals(profile.getEmail()) && updateProfile.getKey() != null)
                profile.setEmail(updateProfile.getEmail());
            userService.save(profile);
            return profile.returnUser();
        }catch (NullPointerException | NumberFormatException | IllegalIdentifierException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Authentication failure",e);
        }
    }
    @PutMapping("/auth/revoke")
    public void revoke(@RequestBody Map<String, String> args){
        try {
            Objects.requireNonNull(args.get("key"));
            Key currentkey = new Key(args.get("key"));
            User profile = userService.findById(currentkey.getId()).get();
            if(!profile.YourKey(currentkey))
                throw new IllegalIdentifierException("");
            byte[] byteCode = new byte[new Random().nextInt(9)+8];
            new Random().nextBytes(byteCode);
            StringBuilder newcode = new StringBuilder();
            for (byte b: byteCode) {
                newcode.append(String.format("%02x", b));
            }
            profile.setCode(newcode.toString());
            userService.save(profile);
        }catch (NumberFormatException | NullPointerException | IllegalIdentifierException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Authentication failure");
        }
    }
}
