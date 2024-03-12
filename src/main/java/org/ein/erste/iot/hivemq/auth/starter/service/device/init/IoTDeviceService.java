package org.ein.erste.iot.hivemq.auth.starter.service.device.init;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.ein.erste.iot.hivemq.auth.starter.domain.IoTDevice;
import org.ein.erste.iot.hivemq.auth.starter.domain.IoTDeviceSecret;
import org.ein.erste.iot.hivemq.auth.starter.service.crypt.MessageCrypt;
import org.ein.erste.iot.hivemq.auth.starter.service.device.crud.IoTDeviceCrudService;
import org.ein.erste.iot.hivemq.auth.starter.settings.config.HiveMQServerCredentialsConfig;
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

@RequiredArgsConstructor
@Service
public class IoTDeviceService {
    private final IoTDeviceCrudService iotDeviceCrudService;
    private final MessageCrypt messageCrypt;
    private final HiveMQServerCredentialsConfig mqttCredentialsConfig;

    public DeviceAuthorizeResponse getCredentials(IoTDeviceSecret deviceSecret) {
        String serialNumber = messageCrypt.decrypt(deviceSecret.getKeyNumber(), deviceSecret.getSecret());
        var device = iotDeviceCrudService.findBySerialNumber(serialNumber).orElseGet(() -> createDevice(serialNumber));
        if (device.isBlacklisted()) throw new ForbiddenException();
        return new DeviceAuthorizeResponse(mqttCredentialsConfig.getDomain(), device.getMqttLogin(), device.getMqttPassword());
    }

    public byte[] getCertificate(IoTDeviceSecret deviceSecret) {
        String serialNumber = messageCrypt.decrypt(deviceSecret.getKeyNumber(), deviceSecret.getSecret());
        var device = iotDeviceCrudService.findBySerialNumber(serialNumber).orElseGet(() -> createDevice(serialNumber));
        if (device.isBlacklisted()) throw new ForbiddenException();

        if (device.getCertificateExpiration().before(new Date())) {
            var certificate = generateCertificate(serialNumber);
            device.setCertificate(certificate.certificate());
            device.setCertificateExpiration(certificate.validTill());
            iotDeviceCrudService.save(device);
        }

        return device.getCertificate();
    }

    private IoTDevice createDevice(String serialNumber){
        var certificate = generateCertificate(serialNumber);
        IoTDevice device = IoTDevice.builder()
                .serialNumber(serialNumber)
                .certificate(certificate.certificate())
                .certificateExpiration(certificate.validTill())
                .blacklisted(false)
                .topics(Set.of())
                .mqttLogin(UUID.randomUUID().toString())
                .mqttPassword(UUID.randomUUID().toString())
                .mqttCredentialsLastRotation(new Date())
                .build();
        iotDeviceCrudService.save(device);
        return device;
    }

    public DeviceCertificate generateCertificate(String serialNumber) {
        var validTill = Date.from(LocalDateTime.now().plusDays(90).toInstant(ZoneOffset.UTC));
        return new DeviceCertificate("stub_certificate".getBytes(), validTill);
    }

}
