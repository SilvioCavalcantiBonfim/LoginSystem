package com.example.login.entities;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class LoginRequest {
    private String email;
    private String password;
}
