package org.ein.erste.iot.hivemq.auth.starter.utils.errors;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;

@Data
public class ForbiddenException extends BasicException{

    public ForbiddenException(String message) {
        super(HttpServletResponse.SC_FORBIDDEN, message);
    }

    public ForbiddenException() {
        super(HttpServletResponse.SC_FORBIDDEN,"error.forbidden");
    }
}
