package com.fih.ishareing.configurations.filter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fih.ishareing.service.token.NoBearerAuthorizationParser;
import com.fih.ishareing.service.token.TokenService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class AuthenticationTokenFilter extends GenericFilterBean {
    private final static String HTTP_HEADER_X_REQUEST_ID = "X-Request-ID";

    private TokenService tokenService;

    public AuthenticationTokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // handle x-request-id
        String requestId = ((HttpServletRequest) request).getHeader(HTTP_HEADER_X_REQUEST_ID);
        ((HttpServletResponse) response).setHeader(HTTP_HEADER_X_REQUEST_ID,
                StringUtils.isBlank(requestId) ? UUID.randomUUID().toString() : requestId);

        // handle x-access-token
        Optional<String> token = NoBearerAuthorizationParser.resolveToken((HttpServletRequest) request);
        if (token.isPresent() && !token.get().trim().equalsIgnoreCase("null")) {
            Authentication auth = StringUtils.isNotBlank(token.get()) ? tokenService.readAuthentication(token.get())
                    : null;
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        chain.doFilter(request, response);
    }

}