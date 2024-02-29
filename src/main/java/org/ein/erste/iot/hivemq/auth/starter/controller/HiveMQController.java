package org.ein.erste.iot.hivemq.auth.starter.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ein.erste.iot.hivemq.auth.starter.controller.util.AuthenticateRequest;
import org.ein.erste.iot.hivemq.auth.starter.controller.util.AuthorizeRequest;
import org.ein.erste.iot.hivemq.auth.starter.service.HiveMQAuthentificationService;
import org.ein.erste.iot.hivemq.auth.starter.utils.Response;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/iot/mqtt")
@RequiredArgsConstructor
@Slf4j
@Validated
public class HiveMQController {

    private final HiveMQAuthentificationService hiveMQAuthentificationService;

    //todo tcp connection
    //mvc config
    //auth for controller
    //configs
    @PostMapping(value = "/authorize")
    public Response<Boolean> authorize(@RequestBody AuthorizeRequest request) {
        return Response.of(hiveMQAuthentificationService.authorize(request.login(), request.topic()));
    }


    @PostMapping(value = "/authenticate")
    public Response<Boolean> authenticate(@RequestBody AuthenticateRequest request) {
        return Response.of(hiveMQAuthentificationService.authenticate(request.login(), request.password()));
    }


}