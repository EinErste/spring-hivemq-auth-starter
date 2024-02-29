package org.ein.erste.iot.hivemq.auth.starter.service.crypt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.ein.erste.iot.hivemq.auth.starter.domain.IoTMessage;
import org.ein.erste.iot.hivemq.auth.starter.settings.AESConfig;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
@Slf4j
@RequiredArgsConstructor
public class IoTDeviceMessageCryptAESImpl implements IoTDeviceMessageCrypt {

    private final AESConfig aesConfig;
    private final ObjectMapper mapper;
    private SecretKeySpec key;
    private SecretKeySpec factoryKey;


    @PostConstruct
    public void init() {
        byte[] decodedKey = Base64.getDecoder().decode(aesConfig.getBase64EncodedKey());
        key = new SecretKeySpec(decodedKey, "AES");
        byte[] decodedFactoryKey = Base64.getDecoder().decode(aesConfig.getBase64EncodedKeyFactory());
        factoryKey = new SecretKeySpec(decodedFactoryKey, "AES");
    }

    @Override
    @SneakyThrows
    public byte[] encrypt(IoTMessage message) {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(mapper.writeValueAsString(message).getBytes());
    }

    @Override
    @SneakyThrows
    public byte[] encryptFactory(IoTMessage message) {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, factoryKey);
        return cipher.doFinal(mapper.writeValueAsString(message).getBytes());
    }

    @Override
    @SneakyThrows
    public IoTMessage decrypt(byte[] message) {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        String decryptedString = new String(cipher.doFinal(message));
        return mapper.readValue(decryptedString, IoTMessage.class);
    }

    @Override
    @SneakyThrows
    public IoTMessage decryptFactory(byte[] message) {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, factoryKey);

        String decryptedString = new String(cipher.doFinal(message));
        return mapper.readValue(decryptedString, IoTMessage.class);
    }
}
