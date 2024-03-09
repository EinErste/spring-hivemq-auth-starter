package org.ein.erste.iot.hivemq.auth.starter.service.device.crud;

import org.ein.erste.iot.hivemq.auth.starter.domain.IoTDevice;

import java.util.Optional;

public interface IoTDeviceCrudService {

    boolean save(IoTDevice device);
    boolean deleteBySerialNumber(String serialNumber);
    Optional<IoTDevice> findBySerialNumber(String serialNumber);
    Optional<IoTDevice> findByMqttLogin(String mqttLogin);
}
