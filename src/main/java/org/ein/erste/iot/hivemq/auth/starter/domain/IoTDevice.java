package org.ein.erste.iot.hivemq.auth.starter.domain;

import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IoTDevice {
    private UUID id;
    private String serialNumber;

    private String mqttLogin;

    private String mqttPassword;

    private Date mqttCredentialsLastRotation;

    private byte[] certificate;

    private Date certificateExpiration;

    @Builder.Default
    private boolean blacklisted = false;

    private Set<String> topics = new HashSet<>();
}
