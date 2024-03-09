package org.ein.erste.iot.hivemq.auth.starter.service.device.init;

import lombok.extern.slf4j.Slf4j;
import org.ein.erste.iot.hivemq.auth.starter.service.crypt.MessageCrypt;
import org.ein.erste.iot.hivemq.auth.starter.service.device.crud.IoTDeviceCrudService;
import org.ein.erste.iot.hivemq.auth.starter.settings.config.HiveMQServerCredentialsConfig;
import org.ein.erste.iot.hivemq.auth.starter.settings.config.NginxServerConfig;
import org.ein.erste.iot.hivemq.auth.starter.utils.DeviceCertificate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Slf4j
@Service
public class IoTDeviceServiceStub extends IoTDeviceService {


    public IoTDeviceServiceStub(IoTDeviceCrudService iotDeviceCrudService,
                                MessageCrypt messageCrypt,
                                HiveMQServerCredentialsConfig mqttCredentialsConfig,
                                NginxServerConfig nginxServerConfig) {
        super(iotDeviceCrudService, messageCrypt, mqttCredentialsConfig, nginxServerConfig);
    }

    @Override
    public DeviceCertificate generateCertificate(String serialNumber) {
        var validTill = Date.from(LocalDateTime.now().plusDays(90).toInstant(ZoneOffset.UTC));
        return new DeviceCertificate("stub_certificate".getBytes(), validTill);
    }
}
