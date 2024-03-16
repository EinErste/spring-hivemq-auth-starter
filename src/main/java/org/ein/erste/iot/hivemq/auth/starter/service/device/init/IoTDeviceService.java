package org.ein.erste.iot.hivemq.auth.starter.service.device.init;

import lombok.RequiredArgsConstructor;
import org.ein.erste.iot.hivemq.auth.starter.domain.IoTDevice;
import org.ein.erste.iot.hivemq.auth.starter.service.device.crud.IoTDeviceCrudService;
import org.ein.erste.iot.hivemq.auth.starter.settings.config.HiveMQServerCredentialsConfig;
import org.ein.erste.iot.hivemq.auth.starter.utils.DeviceAuthorizeResponse;
import org.ein.erste.iot.hivemq.auth.starter.utils.errors.ForbiddenException;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public abstract class IoTDeviceService {
    private final IoTDeviceCrudService iotDeviceCrudService;
    private final HiveMQServerCredentialsConfig mqttCredentialsConfig;

    public DeviceAuthorizeResponse getCredentials(String serialNumber) {
        if (canReturnCredentials(serialNumber)) throw new ForbiddenException();
        var device = iotDeviceCrudService.findBySerialNumber(serialNumber).orElseGet(() -> createDevice(serialNumber));
        return new DeviceAuthorizeResponse(mqttCredentialsConfig.getDomain(), device.getMqttLogin(), device.getMqttPassword());
    }


    private IoTDevice createDevice(String serialNumber){
        IoTDevice device = IoTDevice.builder()
                .serialNumber(serialNumber)
                .blacklisted(false)
                .topics(Set.of())
                .mqttLogin(UUID.randomUUID().toString())
                .mqttPassword(UUID.randomUUID().toString())
                .mqttCredentialsLastRotation(new Date())
                .build();
        iotDeviceCrudService.save(device);
        return device;
    }

    public abstract boolean canReturnCredentials(String serialNumber);

}
