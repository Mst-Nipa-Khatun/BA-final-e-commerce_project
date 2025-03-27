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
//learn us how to unauthorised access can be handled.
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final Logger logger = LoggerFactory.getLogger(this.getClass()); //when unauthorised access can be access then we customs the error log.thats why we use this logger class,here we inject this class
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        logger.error("\n\nURL :{}\nMessage: {}\n", httpServletRequest.getRequestURI(), e.getMessage()); //create a log
        httpServletResponse.setStatus(httpServletResponse.SC_UNAUTHORIZED); //status 401 unauthorised
        httpServletResponse.sendError(httpServletResponse.SC_UNAUTHORIZED, "Unauthorized Request");  //showing message
    }
}
