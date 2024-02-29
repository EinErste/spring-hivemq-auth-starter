package org.ein.erste.iot.hivemq.auth.starter.service.crud;

import org.ein.erste.iot.hivemq.auth.starter.domain.IoTDevice;

import java.util.Optional;
import java.util.UUID;

public interface IoTDeviceCrudService {

    boolean save(IoTDevice device);
    boolean deleteById(UUID id);
    Optional<IoTDevice> findById(UUID id);
    Optional<IoTDevice> findBySerialNumber(String serialNumber);
    Optional<IoTDevice> findByMqttLogin(String mqttLogin);
}
