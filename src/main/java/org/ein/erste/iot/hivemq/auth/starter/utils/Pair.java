package org.ein.erste.iot.hivemq.auth.starter.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pair<F, S> {
    private F first;
    private S second;
}
