package org.ein.erste.iot.hivemq.auth.starter.domain;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IoTMessage {
    public enum Type {
        REQUEST_CREDENTIALS,
        CREDENTIALS,
        OK
    }

    private String serialNumber;
    private Type type;
    private Map<String, Object> data;
}
