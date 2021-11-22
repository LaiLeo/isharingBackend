package com.fih.ishareing.configurations.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fih.ishareing.controller.base.error.ApiErrorResponse;
import com.fih.ishareing.errorHandling.ErrorHandlingConstants;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response,
            final AuthenticationException e) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        PrintWriter writer = response.getWriter();
        writer.write(mapper.writeValueAsString(new ApiErrorResponse(ErrorHandlingConstants.ERROR_BUNDLE.invalidToken)));
        writer.flush();
    }
}