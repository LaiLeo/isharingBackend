package com.fih.ishareing.service.token;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class NoBearerAuthorizationParser {
    public static Optional<String> resolveToken(HttpServletRequest requeset) {
        String token = requeset.getHeader("X-Access-Token");
        if (StringUtils.isNotBlank(token))
            return Optional.of(token.trim());
        else
            return Optional.empty();
    }
}
