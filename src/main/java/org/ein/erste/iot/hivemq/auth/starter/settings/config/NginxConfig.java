package org.ein.erste.iot.hivemq.auth.starter.settings.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NginxConfig {
    private String certificatePubKey;
}
