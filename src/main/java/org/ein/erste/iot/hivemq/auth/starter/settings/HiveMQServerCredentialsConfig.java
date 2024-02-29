package org.ein.erste.iot.hivemq.auth.starter.settings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HiveMQServerCredentialsConfig {
    private String login;
    private String password;
}
