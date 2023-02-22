package com.example.login.entities;

import jakarta.persistence.*;
import lombok.*;

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

}
