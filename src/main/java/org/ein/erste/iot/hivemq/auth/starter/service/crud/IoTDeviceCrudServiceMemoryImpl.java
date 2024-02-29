package org.ein.erste.iot.hivemq.auth.starter.service.crud;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ein.erste.iot.hivemq.auth.starter.domain.IoTDevice;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class IoTDeviceCrudServiceMemoryImpl implements IoTDeviceCrudService {
    private Map<UUID, IoTDevice> devicesId = new ConcurrentHashMap<>();
    private Map<String, IoTDevice> devicesSerial = new ConcurrentHashMap<>();
    private Map<String, IoTDevice> devicesLogin = new ConcurrentHashMap<>();
    @Override
    public boolean save(IoTDevice device) {
        devicesId.put(device.getId(), device);
        devicesSerial.put(device.getSerialNumber(), device);
        devicesLogin.put(device.getMqttLogin(), device);
        return true;
    }

    @Override
    public boolean deleteById(UUID id) {
        devicesSerial.remove(devicesId.get(id).getSerialNumber());
        devicesLogin.remove(devicesId.get(id).getMqttLogin());
        devicesId.remove(id);
        return false;
    }

    @Override
    public Optional<IoTDevice> findById(UUID id) {
        return devicesId.get(id) == null ? Optional.empty() : Optional.of(devicesId.get(id));
    }

    @Override
    public Optional<IoTDevice> findBySerialNumber(String serialNumber) {
        return devicesSerial.get(serialNumber) == null ? Optional.empty() : Optional.of(devicesSerial.get(serialNumber));
    }

    @Override
    public Optional<IoTDevice> findByMqttLogin(String mqttLogin) {
        return devicesLogin.get(mqttLogin) == null ? Optional.empty() : Optional.of(devicesLogin.get(mqttLogin));
    }
}
