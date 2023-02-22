package com.example.login.tools;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import java.util.Arrays;
import java.util.Base64;

public class Crypt {
    private static SecretKey secretKey;
    private static byte[] key;

    public static void setKey(final String _key){
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA-1");
            key = Arrays.copyOf(sha.digest(_key.getBytes("UTF-8")), 16);
            secretKey = new SecretKeySpec(key, "AES");
        }catch (NoSuchAlgorithmException | UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    public static String encrypt(final String str, final String _key){
        try{
            setKey(_key);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
        }catch (Exception e){

        }
        return null;
    }

    public static String decrypt(final String str, final String _key){
        try {
            setKey(_key);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE,secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(str)));
        }catch (Exception e){

        }
        return null;
    }
}
