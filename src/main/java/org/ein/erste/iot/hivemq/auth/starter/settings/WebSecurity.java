package org.ein.erste.iot.hivemq.auth.starter.settings;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ein.erste.iot.hivemq.auth.starter.settings.auth.HiveMQAuthFilter;
import org.ein.erste.iot.hivemq.auth.starter.settings.config.HiveMQServerCredentialsConfig;
import org.ein.erste.iot.hivemq.auth.starter.utils.Response;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.ein.erste.iot.hivemq.auth.starter.settings.auth.AuthConstants.HIVEMQ_API_TOKEN;
import static org.ein.erste.iot.hivemq.auth.starter.utils.Utils.writeObjectToResponse;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HiveMQAuthFilter hiveMQAuthFilter) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exp -> exp
                        .authenticationEntryPoint(
                                (HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) -> {
                                    writeObjectToResponse(Response.of(HttpServletResponse.SC_UNAUTHORIZED, "error.auth.required"), response);
                                }))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/error").permitAll());
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/api/iot/device/credentials").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/iot/device/certificate").permitAll()
                .anyRequest().authenticated()
        );
        http.addFilterBefore(hiveMQAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public HiveMQAuthFilter hiveMQAuthFilter(HiveMQServerCredentialsConfig credentialsConfig) {
        return new HiveMQAuthFilter(credentialsConfig.getApiToken());
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowCredentials(false);
        configuration.setAllowedHeaders(getCorsAllowedHeaders());
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS"));
        source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
        return source;
    }

    private List<String> getCorsAllowedHeaders() {
        return Arrays.asList(HIVEMQ_API_TOKEN, "User-Agent", "Cache-Control", "X-Remote-IP", "X-Forwarded-List", "X-FORWARDED-FOR",
                             "Content-Type", "Accept-Language", "X-Requested-With", HttpHeaders.IF_MODIFIED_SINCE, HttpHeaders.LAST_MODIFIED);
    }
}
