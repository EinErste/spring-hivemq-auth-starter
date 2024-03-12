package org.ein.erste.iot.hivemq.auth.starter.utils.errors;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;

@Data
public class NotFoundException extends BasicException{

    public NotFoundException(String message) {
        super(HttpServletResponse.SC_NOT_FOUND, message);
    }

    public NotFoundException() {
        super(HttpServletResponse.SC_NOT_FOUND,"error.not.found");
    }
}
