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
public class IoTDeviceSecret {
    private int keyNumber;
    private String secret;
}
