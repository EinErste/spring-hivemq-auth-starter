package org.ein.erste.iot.hivemq.auth.starter.settings;

import org.ein.erste.iot.hivemq.auth.starter.settings.config.HiveMQServerCredentialsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneralConfiguration {
    @Bean
    public HiveMQServerCredentialsConfig stubHiveMQServerCredentialsConfig() {
        throw new IllegalStateException("HiveMQServerCredentialsConfig bean is not provided");
    }

}
