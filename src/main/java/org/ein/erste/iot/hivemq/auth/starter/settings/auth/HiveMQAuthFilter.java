package org.ein.erste.iot.hivemq.auth.starter.settings.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.ein.erste.iot.hivemq.auth.starter.utils.HiveMQAuthority;
import org.ein.erste.iot.hivemq.auth.starter.utils.errors.ForbiddenException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Set;

@RequiredArgsConstructor
public class HiveMQAuthFilter extends GenericFilterBean {

    private final String authToken;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String token = extractToken((HttpServletRequest) request);
        if (token != null && validateToken(token)) {
            SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        } else {
            throw new ForbiddenException();
        }
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AuthConstants.HIVEMQ_API_TOKEN);
        if (bearerToken == null) throw new ForbiddenException();
        return bearerToken;
    }

    private boolean validateToken(String token) {
        return authToken.equals(token);
    }

    private UsernamePasswordAuthenticationToken getAuthentication() {
        return new UsernamePasswordAuthenticationToken(null, null, Set.of(new HiveMQAuthority()));
    }
}