package com.example.login.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UpdateProfile {
    private String key;
    private String firstname;
    private String lastname;
}
