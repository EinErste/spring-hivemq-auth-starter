package org.ein.erste.iot.hivemq.auth.starter.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IoTDeviceSecret {
    private int keyNumber;
    private String secret;
}
