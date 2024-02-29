package org.ein.erste.iot.hivemq.auth.starter.service.crypt;

import org.ein.erste.iot.hivemq.auth.starter.domain.IoTMessage;

public interface IoTDeviceMessageCrypt {

    byte[] encrypt(IoTMessage message);
    byte[] encryptFactory(IoTMessage message);
    IoTMessage decrypt(byte[] message);
    IoTMessage decryptFactory(byte[] message);

}
