package com.satya.service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class AESCBCExample {

    public static void main(String[] args) {
        try {
            // Read Base64 encoded AES key from file
            byte[] keyBytes = Base64.getDecoder().decode(new String(Files.readAllBytes(Paths.get("aes_key_base64.txt"))));
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

            // Read Base64 encoded IV from file
            byte[] ivBytes = Base64.getDecoder().decode(new String(Files.readAllBytes(Paths.get("iv_base64.txt"))));
            IvParameterSpec iv = new IvParameterSpec(ivBytes);

            // Encrypt a sample text
            String plainText = "Hello, World!";
            String encryptedText = encrypt(plainText, secretKey, iv);
            System.out.println("Encrypted Text: " + encryptedText);

            // Decrypt the encrypted text
            String decryptedText = decrypt(encryptedText, secretKey, iv);
            System.out.println("Decrypted Text: " + decryptedText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String plainText, SecretKeySpec key, IvParameterSpec iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedText, SecretKeySpec key, IvParameterSpec iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }
}
