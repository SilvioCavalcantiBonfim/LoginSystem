package com.example.login.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashMap;
import java.util.Map;

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
            return this.password.compareTo(pwd) == 0;
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

}
