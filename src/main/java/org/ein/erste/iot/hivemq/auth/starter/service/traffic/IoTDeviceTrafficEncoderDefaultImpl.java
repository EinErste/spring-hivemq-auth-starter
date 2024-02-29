package org.ein.erste.iot.hivemq.auth.starter.service.traffic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ein.erste.iot.hivemq.auth.starter.domain.IoTMessage;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class IoTDeviceTrafficEncoderDefaultImpl implements IoTDeviceTrafficEncoder {
    @Override
    public byte[] encode(IoTMessage message) {
        return new byte[0];
    }

    @Override
    public IoTMessage decode(byte[] message) {
        return null;
    }
}
