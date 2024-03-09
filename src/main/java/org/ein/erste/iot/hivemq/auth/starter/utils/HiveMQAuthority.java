package org.ein.erste.iot.hivemq.auth.starter.utils;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class HiveMQAuthority implements GrantedAuthority {

    public final String HIVEMQ_AUTHORITY = "HIVEMQ_AUTHORITY";

    @Override
    public String getAuthority() {
        return HIVEMQ_AUTHORITY;
    }
}
