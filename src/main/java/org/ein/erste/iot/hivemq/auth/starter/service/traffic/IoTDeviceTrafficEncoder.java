package org.ein.erste.iot.hivemq.auth.starter.service.traffic;

import org.ein.erste.iot.hivemq.auth.starter.domain.IoTMessage;

public interface IoTDeviceTrafficEncoder {

    byte[] encode(IoTMessage message);
    IoTMessage decode(byte[] message);

}
