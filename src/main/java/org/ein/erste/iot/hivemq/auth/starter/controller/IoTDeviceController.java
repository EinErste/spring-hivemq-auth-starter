package org.ein.erste.iot.hivemq.auth.starter.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ein.erste.iot.hivemq.auth.starter.service.device.init.IoTDeviceService;
import org.ein.erste.iot.hivemq.auth.starter.settings.config.NginxConfig;
import org.ein.erste.iot.hivemq.auth.starter.utils.DeviceAuthorizeResponse;
import org.ein.erste.iot.hivemq.auth.starter.utils.Response;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/iot/device")
@RequiredArgsConstructor
@Slf4j
@Validated
public class IoTDeviceController {

    private final IoTDeviceService iotDeviceService;
    private final NginxConfig nginxConfig;

    @GetMapping(value = "/credentials")
    public Response<DeviceAuthorizeResponse> getCredentials(@RequestParam("serial_number") String deviceSecret) {
        return Response.of(iotDeviceService.getCredentials(deviceSecret));
    }

    @GetMapping(value = "/certificate")
    public Response<String> getCredentials() {
        return Response.of(nginxConfig.getCertificatePubKey());
    }
}