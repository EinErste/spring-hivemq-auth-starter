package org.ein.erste.iot.hivemq.auth.starter.service.device.init;

import lombok.RequiredArgsConstructor;
import org.ein.erste.iot.hivemq.auth.starter.domain.IoTDevice;
import org.ein.erste.iot.hivemq.auth.starter.service.device.crud.IoTDeviceCrudService;
import org.ein.erste.iot.hivemq.auth.starter.settings.config.HiveMQServerCredentialsConfig;
import org.ein.erste.iot.hivemq.auth.starter.utils.DeviceAuthorizeResponse;
import org.ein.erste.iot.hivemq.auth.starter.utils.errors.ForbiddenException;
import org.ein.erste.iot.hivemq.auth.starter.utils.errors.NotFoundException;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public abstract class IoTDeviceService {
    private final IoTDeviceCrudService iotDeviceCrudService;
    private final HiveMQServerCredentialsConfig mqttCredentialsConfig;

    public DeviceAuthorizeResponse getCredentials(String serialNumber) {
        if (canReturnCredentials(serialNumber)) throw new ForbiddenException();
        var device = iotDeviceCrudService.findBySerialNumber(serialNumber).orElseThrow(NotFoundException::new);
        if (!device.hasCredentials()) {
            device = createCredentials(device);
        }
        return new DeviceAuthorizeResponse(mqttCredentialsConfig.getDomain(), device.getMqttLogin(), device.getMqttPassword());
    }

    private IoTDevice createCredentials(IoTDevice device){
        device.setMqttLogin(UUID.randomUUID().toString());
        device.setMqttPassword(UUID.randomUUID().toString());
        device.setMqttCredentialsLastRotation(new Date());
        return iotDeviceCrudService.save(device);
    }

    public abstract boolean canReturnCredentials(String serialNumber);

}
