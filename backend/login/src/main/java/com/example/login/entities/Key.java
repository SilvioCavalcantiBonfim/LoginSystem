package com.example.login.entities;

import com.example.login.tools.Crypt;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Key {
    @Getter
    @Setter
    private Long id;


    private final String __key = "Se tu vens por exemplo as quatro da tarde, desde as tres eu começarei a ser feliz";
    @Getter
    @Setter
    private String code;
    private String privateKey;
    public Key(Long id, String code) {
        this.id = id;
        this.code = code;
        byte[] privateCode = new byte[16];
        new Random().nextBytes(privateCode);
        StringBuilder privateKey = new StringBuilder();
        for (byte b: privateCode) {
            privateKey.append(String.format("%02x", b));
        }
        this.privateKey = privateKey.toString();
    }

    public Key(String _key) throws NumberFormatException{
        List<String> args = Arrays.asList(Crypt.decrypt(_key,__key).split(":"));
        this.id = Long.parseLong(args.get(0));
        this.code = args.get(1);
        this.privateKey = args.get(2);
    }


    public String Encode(){
        return Crypt.encrypt(String.format("%d:%s:%s",this.id,this.code,privateKey.toString()),__key);
    }

    @Override
    public String toString() {
        return "key{" +
                "id=" + id +
                ", code='" + code + '\'' +
                '}';
    }
}
