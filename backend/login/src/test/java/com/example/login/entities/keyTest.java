package com.example.login.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class keyTest {

    private Key key = null;

    @BeforeEach
    void update(){
        key = new Key(0L,"teste");
    }
    @Test
    @DisplayName("Key class: toString")
    void tostringkey(){
        Assertions.assertEquals("key{id=0, code='teste'}",key.toString());
    }
    @Test
    @DisplayName("Key class: generete key encode")
    void encodekey(){
        Assertions.assertEquals(64,key.Encode().length());
    }

    @Test
    @DisplayName("Key class: construct key is valid")
    void constructekey(){
        Key _key = new Key("uAoHNOVKxhlonNl6fGDXGENf61U1/cI1JirO/QPJ0V5dDLd7OrjhBPXUEeg7lTZa");
        System.out.println(_key.toString());
    }

    @Test
    @DisplayName("Key class: construct key is invalid")
    void constructeinvalidkey(){
        Assertions.assertThrowsExactly(NumberFormatException.class, () -> new Key("UAoHNOVKxhlonNl6fGDXGENf61U1/cI1JirO/QPJ0V5dDLd7OrjhBPXUEeg7lTZa"));
    }

    @Test
    @DisplayName("Key class: construct key is invalid")
    void constructenullkey(){
        Assertions.assertThrowsExactly(NumberFormatException.class, () -> new Key(null));
    }

//
}