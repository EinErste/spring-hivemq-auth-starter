package org.ein.erste.iot.hivemq.auth.starter.service.crypt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageCryptAESMemoryImpl extends MessageCryptAES {

    private Map<Integer, String> keys = new ConcurrentHashMap<>();
    private AtomicInteger nextKeyId = new AtomicInteger(0);


    @Override
    public int addKey(int keyNumber, String key) {
        keys.put(keyNumber, key);
        return keyNumber;
    }

    @Override
    public Optional<String> findKeyByNumber(int keyNumber) {
        return keys.containsKey(keyNumber) ? Optional.of(keys.get(keyNumber)) : Optional.empty();
    }

    public int addKey(String key) {
        int id = getNextKeyId();
        return addKey(id, key);
    }

    private int getNextKeyId(){
        return nextKeyId.getAndIncrement();
    }
}
