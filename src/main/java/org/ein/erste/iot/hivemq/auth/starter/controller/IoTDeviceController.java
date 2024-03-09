package org.ein.erste.iot.hivemq.auth.starter.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ein.erste.iot.hivemq.auth.starter.domain.IoTDeviceSecret;
import org.ein.erste.iot.hivemq.auth.starter.service.device.init.IoTDeviceService;
import org.ein.erste.iot.hivemq.auth.starter.utils.DeviceAuthorizeResponse;
import org.ein.erste.iot.hivemq.auth.starter.utils.Response;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/iot/device")
@RequiredArgsConstructor
@Slf4j
@Validated
public class IoTDeviceController {

    private final IoTDeviceService iotDeviceService;

    @PostMapping(value = "/credentials")
    public Response<DeviceAuthorizeResponse> getCredentials(@RequestBody IoTDeviceSecret deviceSecret) {
        return Response.of(iotDeviceService.getCredentials(deviceSecret));
    }

    @PostMapping(value = "/certificate")
    public byte[] getCertificate(@RequestBody IoTDeviceSecret deviceSecret) {
        return iotDeviceService.getCertificate(deviceSecret);
    }


}