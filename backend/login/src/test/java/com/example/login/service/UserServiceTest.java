package com.example.login.service;

import com.example.login.entities.User;
import com.example.login.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("UserServiceTest")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest{
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("GenerateKey: Receive User null.")
    @Order(1)
    void UserIsNullTest(){
        User user = null;
        Assertions.assertThrows(NullPointerException.class, () -> userService.GenerateKey(user));
    }

    @Test
    @DisplayName("GenerateKey: Receive User not null.")
    @Order(2)
    void UserIsNotNullTest(){
        User user = new User(0L, "nome1", "nome2", "teste@teste.com", "senha", "codigo");
        String Code = userService.GenerateKey(user);
        System.out.println(Code);
        Assertions.assertEquals(String.class, Code.getClass());
    }

    @Test
    @DisplayName("keyPart: receive key with format valid.")
    @Order(3)
    void keyPartValid(){
        String key = "LpOVI5vHU9r88y1T2U7KolLHbo9hFQoAUK4j04HClzU4uYPGSyCEE5jOHISC1Jzw";
        List<String> compareto = new ArrayList<>();
        compareto.add("teste@teste.com");
        compareto.add("code");
        Assertions.assertEquals(userService.keyPart(key), compareto);
    }
    @Test
    @DisplayName("keyPart: receive key with format invalid.")
    @Order(4)
    void keyPartInvalid(){
        String key = "LpOVI5vHU9r88y1T2U7KolLHbo9hFQoAUK4j04HClzU4uYPGSyCEE5jOHISC1JzW";
        Assertions.assertThrows(NullPointerException.class, () -> userService.keyPart(key));
    }

    @Test
    @DisplayName("keyIsValid: receive key is valid.")
    @Order(5)
    void KeyValidTest(){
        String email = "teste@teste.com";
        List<User> registro = CreateListUser(0);
        Mockito.when(userRepository.findByEmail(ArgumentMatchers.eq(email))).thenReturn(registro);
        User testeUser = new User(0L, "teste", "teste", "teste@teste.com", "senha", "code");
        String Code = userService.GenerateKey(testeUser);
        Assertions.assertTrue(userService.keyIsValid(Code));
    }

    @Test
    @DisplayName("keyIsValid: receive key is not valid with format valid.")
    @Order(6)
    void KeyNotValidTest(){
        String email = "teste@teste.com";
        List<User> reg = CreateListUser(1);
        Mockito.when(userRepository.findByEmail(ArgumentMatchers.eq(email))).thenReturn(reg);
        User testeUser = new User(0L, "teste", "teste", "teste@teste.com", "senha", "code");
        String Code = userService.GenerateKey(testeUser);
        Assertions.assertFalse(userService.keyIsValid(Code));
    }


    @Test
    @DisplayName("keyIsValid: receive key with format invalid.")
    @Order(7)
    void KeyDecryptNotCountTwoDot(){
        Assertions.assertFalse(userService.keyIsValid("estaeachave"));
    }


    private List<User> CreateListUser(int i) {
        List<String> codes = new ArrayList<>();
        codes.add("code");
        codes.add("aaaa");
        User user = new User(0L, "teste", "teste", "teste@teste.com", "senha", codes.get(i));
        List<User> users = new ArrayList<User>();
        users.add(user);
        return users;
    }


}
