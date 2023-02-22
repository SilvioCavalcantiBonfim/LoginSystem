package com.example.login.service;

import com.example.login.entities.User;
import com.example.login.repository.UserRepository;
import com.example.login.tools.Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.IllegalBlockSizeException;
import java.util.*;

@Service
public class UserService implements UserServiceInterface{
    @Autowired
    private UserRepository userRepository;

    private final String key = "Se tu vens por exemplo as quatro da tarde, desde as tres eu começarei a ser feliz";
    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Override
    public List<String> keyPart(String _key) throws NullPointerException{
        return Arrays.asList(Crypt.decrypt(_key,key).split(":")).subList(0,2);
    }
    @Override
    public boolean keyIsValid(String _key){
        try {
            List<String> key_values = this.keyPart(_key);
            List<User> register = userRepository.findByEmail(key_values.get(0));
            return register.get(0).getCode().compareTo(key_values.get(1)) == 0;
        }catch (NullPointerException e){
            return false;
        }
    }
    @Override
    public String GenerateKey(User user) throws NullPointerException{
        Objects.requireNonNull(user);

        //cria a chave privada
        byte[] privateCode = new byte[8];
        new Random().nextBytes(privateCode);
        StringBuilder privateKey = new StringBuilder();
        for (byte b: privateCode) {
            privateKey.append(String.format("%02x", b));
        }

        //cria o codigo privado
//        privateCode = new byte[8];
//        new Random().nextBytes(privateCode);
//        StringBuilder newCode = new StringBuilder();
//        for (byte b: privateCode) {
//            newCode.append(String.format("%02x", b));
//        }
//        user.setCode(newCode.toString());
//        userRepository.save(user);
        return Crypt.encrypt(user.getEmail()+":"+user.getCode()+":"+privateKey, key);
    }

    public List<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
