package org.ein.erste.iot.hivemq.auth.starter.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ein.erste.iot.hivemq.auth.starter.controller.util.AuthenticateRequest;
import org.ein.erste.iot.hivemq.auth.starter.controller.util.AuthorizeRequest;
import org.ein.erste.iot.hivemq.auth.starter.service.HiveMQAuthentificationService;
import org.ein.erste.iot.hivemq.auth.starter.utils.HiveMQAuthority;
import org.ein.erste.iot.hivemq.auth.starter.utils.Response;
import org.ein.erste.iot.hivemq.auth.starter.utils.errors.ForbiddenException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/iot/mqtt")
@RequiredArgsConstructor
@Slf4j
@Validated
public class HiveMQController {

    private final HiveMQAuthentificationService hiveMQAuthentificationService;

    //mvc config auth for controller
    @PostMapping(value = "/authorize")
    public Response<Boolean> authorize(@RequestBody AuthorizeRequest request) {
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new HiveMQAuthority()))
            throw new ForbiddenException();

        return Response.of(hiveMQAuthentificationService.authorize(request.login(), request.topic()));
    }


    @PostMapping(value = "/authenticate")
    public Response<Boolean> authenticate(@RequestBody AuthenticateRequest request) {
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new HiveMQAuthority()))
            throw new ForbiddenException();

        return Response.of(hiveMQAuthentificationService.authenticate(request.login(), request.password()));
    }


}