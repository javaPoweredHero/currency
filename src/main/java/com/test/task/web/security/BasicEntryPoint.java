package com.test.task.web.security;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class BasicEntryPoint extends BasicAuthenticationEntryPoint {

    @Autowired
    private ExceptionWriter writer;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex)
            throws IOException {
        writer.writeException(ex, HttpStatus.UNAUTHORIZED, response);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("TEST_APPLICATION");
        super.afterPropertiesSet();
    }
}
