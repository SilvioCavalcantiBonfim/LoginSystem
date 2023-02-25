package com.example.login.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;

    private String password;

    private String code;

    public boolean isPassowrd(String pwd){
        try{
            return this.password.equals(pwd);
        }catch (Exception e){
            return false;
        }
    }

    public Map<String, Object> returnUser(){
        Map<String,Object> rt = new LinkedHashMap<>();
        String[] name = {this.firstname, this.lastname};
        rt.put("name", name);
        rt.put("email",this.email);
        return rt;
    }

    public Key GenerateKey(){
        return new Key(this.id, this.code);
    }

    public boolean YourKey(String _key){
        try {
            Key currentKey = new Key(_key);
            return this.id.equals(currentKey.getId()) && this.code.equals(currentKey.getCode());
        }catch (NumberFormatException e){
            return false;
        }
    }

    public boolean YourKey(Key _key){
        try {
            return this.id.equals(_key.getId()) && this.code.equals(_key.getCode());
        }catch (NumberFormatException e){
            return false;
        }
    }
}
