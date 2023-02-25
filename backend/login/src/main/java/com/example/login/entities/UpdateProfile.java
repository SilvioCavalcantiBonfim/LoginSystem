package com.example.login.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class UpdateProfile {
    private String key;
    private String firstname;
    private String lastname;
    private String email;
}
