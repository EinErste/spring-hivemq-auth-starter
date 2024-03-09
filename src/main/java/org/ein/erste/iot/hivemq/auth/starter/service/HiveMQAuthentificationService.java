package org.ein.erste.iot.hivemq.auth.starter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ein.erste.iot.hivemq.auth.starter.service.device.crud.IoTDeviceCrudService;
import org.ein.erste.iot.hivemq.auth.starter.settings.config.HiveMQServerCredentialsConfig;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HiveMQAuthentificationService {
    private final IoTDeviceCrudService iotDeviceCrudService;
    private final HiveMQServerCredentialsConfig hiveMQServerCredentialsConfig;

    public boolean authenticate(String login, String password) {
        if (hiveMQServerCredentialsConfig.getLogin().equals(login) &&
            hiveMQServerCredentialsConfig.getPassword().equals(password)) {
            return true;
        }
        var deviceWrapped = iotDeviceCrudService.findByMqttLogin(login);
        if (deviceWrapped.isPresent()) {
            var device = deviceWrapped.get();
            return device.getMqttPassword().equals(password);
        }
        return false;
    }

    public boolean authorize(String login, String topic) {
        if (hiveMQServerCredentialsConfig.getLogin().equals(login)) {
            return true;
        }
        var deviceWrapped = iotDeviceCrudService.findByMqttLogin(login);
        if (deviceWrapped.isPresent() && topic != null) {
            var device = deviceWrapped.get();
            return device.getTopics().contains(topic);
        }
        return false;
    }
}
