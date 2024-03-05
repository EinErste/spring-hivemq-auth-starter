package org.ein.erste.iot.hivemq.auth.starter.service.crypt;


import java.util.Optional;

public interface MessageCrypt {

    int addKey(int keyNumber, String key);
    Optional<String> findKeyByNumber(int keyNumber);
    String encrypt(int keyNumber, String message);
    String decrypt(int keyNumber, String message);

}
