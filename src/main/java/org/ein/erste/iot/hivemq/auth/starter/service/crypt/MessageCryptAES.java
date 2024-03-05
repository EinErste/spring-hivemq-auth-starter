package org.ein.erste.iot.hivemq.auth.starter.service.crypt;

import lombok.SneakyThrows;
import org.ein.erste.iot.hivemq.auth.starter.utils.errors.NotFoundException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public abstract class MessageCryptAES implements MessageCrypt {

    @Override
    @SneakyThrows
    public String encrypt(int keyNumber, String message) {
        String base64EncodedKey = findKeyByNumber(keyNumber).orElseThrow(() -> new NotFoundException("Key not found"));

        byte[] decodedKey = Base64.getDecoder().decode(base64EncodedKey);
        SecretKeySpec key = new SecretKeySpec(decodedKey, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encryptedBytes = cipher.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }


    @Override
    @SneakyThrows
    public String decrypt(int keyNumber, String message) {
        String base64EncodedKey = findKeyByNumber(keyNumber).orElseThrow(() -> new NotFoundException("Key not found"));

        byte[] decodedKey = Base64.getDecoder().decode(base64EncodedKey);
        SecretKeySpec key = new SecretKeySpec(decodedKey, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(message));
        return new String(decryptedBytes);
    }

}
