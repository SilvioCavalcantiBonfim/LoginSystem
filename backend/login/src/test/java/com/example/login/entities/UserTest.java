package com.example.login.entities;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTest {
    private User user = null;

    @BeforeEach
    void update(){
        user = new User(0L,"teste","teste","teste@teste.com","password","code");
    }
    @Test
    @DisplayName("Check if password is correct by passing correct password")
    void verificateIfIsPassword_casePasswordCorrect() {
        Assertions.assertTrue(user.isPassowrd("password"));
    }

    @Test
    @DisplayName("Check if password is correct by passing incorrect password")
    void verificateIfIsPassword_casePasswordIncorrect() {
        Assertions.assertFalse(user.isPassowrd("senha"));
    }

    @Test
    @DisplayName("Check if password is correct by passing null")
    void verificateIfIsPassword_casePasswordNull() {
        Assertions.assertFalse(user.isPassowrd(null));
    }
}