package org.ein.erste.iot.hivemq.auth.starter.service.device.init;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.ein.erste.iot.hivemq.auth.starter.domain.IoTDevice;
import org.ein.erste.iot.hivemq.auth.starter.domain.IoTDeviceSecret;
import org.ein.erste.iot.hivemq.auth.starter.service.crypt.MessageCrypt;
import org.ein.erste.iot.hivemq.auth.starter.service.device.crud.IoTDeviceCrudService;
import org.ein.erste.iot.hivemq.auth.starter.settings.HiveMQServerCredentialsConfig;
import org.ein.erste.iot.hivemq.auth.starter.settings.NginxServerConfig;
import org.ein.erste.iot.hivemq.auth.starter.utils.DeviceAuthorizeResponse;
import org.ein.erste.iot.hivemq.auth.starter.utils.DeviceCertificate;
import org.ein.erste.iot.hivemq.auth.starter.utils.errors.ForbiddenException;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

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
        return new DeviceCertificate("test".getBytes(), validTill);
    }
}
