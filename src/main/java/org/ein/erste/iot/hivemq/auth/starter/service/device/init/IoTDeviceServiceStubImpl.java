package org.ein.erste.iot.hivemq.auth.starter.service.device.init;

import org.ein.erste.iot.hivemq.auth.starter.service.device.crud.IoTDeviceCrudService;
import org.ein.erste.iot.hivemq.auth.starter.settings.config.HiveMQServerCredentialsConfig;
import org.springframework.stereotype.Service;

@Service
public class IoTDeviceServiceStubImpl extends IoTDeviceService {
    private final IoTDeviceCrudService iotDeviceCrudService;

    public IoTDeviceServiceStubImpl(IoTDeviceCrudService iotDeviceCrudService, HiveMQServerCredentialsConfig mqttCredentialsConfig, IoTDeviceCrudService iotDeviceCrudService1) {
        super(iotDeviceCrudService, mqttCredentialsConfig);
        this.iotDeviceCrudService = iotDeviceCrudService1;
    }

    @Override
    public boolean canReturnCredentials(String serialNumber) {
        var wrappedDevice = iotDeviceCrudService.findBySerialNumber(serialNumber);
        return wrappedDevice.isEmpty();
    }
}
