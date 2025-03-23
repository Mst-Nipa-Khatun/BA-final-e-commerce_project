package com.nipa.agroneed.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;


import java.io.IOException;

@Configuration
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        logger.error("Message: {}", e.getMessage());
        httpServletResponse.setStatus(httpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.sendError(httpServletResponse.SC_UNAUTHORIZED, "Unauthorized Request");
    }
}
