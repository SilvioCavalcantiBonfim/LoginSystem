package com.example.login.service;

import com.example.login.entities.User;
import com.example.login.repository.UserRepository;
import com.example.login.tools.Crypt;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.crypto.IllegalBlockSizeException;
import java.util.*;

@Service
public class UserService implements UserServiceInterface{
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    @Override
    public void save(@NonNull User user){
        userRepository.save(user);
    }

    @Override
    public List<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
