package com.example.login.entities;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    @DisplayName("Create key with user")
    void create_key(){
        System.out.println(user.GenerateKey().Encode());
    }

    @Test
    @DisplayName("Key encode is valid and key belong user")
    void yorKeyandvalid(){
        String currentKey = "A+/NCiGTwiCIKem1Mv9xX/CrmG/DV8fM59ZHxtJlKp+hIjbf20tFkq3XlW13ZSJk";
        Assertions.assertTrue(user.YourKey(currentKey));
    }

    @Test
    @DisplayName("key encode is invalid")
    void yorKeyisinvalid(){
        String currentKey = "a+/NCiGTwiCIKem1Mv9xX/CrmG/DV8fM59ZHxtJlKp+hIjbf20tFkq3XlW13ZSJk";
        Assertions.assertFalse(user.YourKey(currentKey));
    }
    @Test
    @DisplayName("Key encode is valid and key not belong user")
    void yorKeyisvalidandnotbelonguser(){
        User auxUser = new User(1L,"teste","teste","teste@teste.com","teste", "code");
        Assertions.assertFalse(user.YourKey(auxUser.GenerateKey().Encode()));
    }

    @Test
    @DisplayName("Key object is valid and key belong user")
    void yorObjectKeyandvalid(){
        Assertions.assertTrue(user.YourKey(user.GenerateKey()));
    }

    @Test
    @DisplayName("key object is invalid")
    void yorKeyObjectisinvalid(){
        String currentKey = "a+/NCiGTwiCIKem1Mv9xX/CrmG/DV8fM59ZHxtJlKp+hIjbf20tFkq3XlW13ZSJk";
        Assertions.assertThrows(NumberFormatException.class, () -> user.YourKey(new Key(currentKey)));
    }
    @Test
    @DisplayName("Key object is valid and key not belong user")
    void yorKeyObjectisvalidandnotbelonguser(){
        User auxUser = new User(1L,"teste","teste","teste@teste.com","teste", "code");
        Assertions.assertFalse(user.YourKey(auxUser.GenerateKey()));
    }
}