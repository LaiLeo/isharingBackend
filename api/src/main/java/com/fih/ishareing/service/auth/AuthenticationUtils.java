package com.fih.ishareing.service.auth;

import com.fih.ishareing.errorHandling.exceptions.TokenInValidException;
import com.fih.ishareing.service.user.model.AuthUser;
import com.fih.ishareing.service.user.model.IeqUser;
import com.google.common.base.Preconditions;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtils {
    public static final int getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AuthUser) {
            return ((AuthUser) authentication.getPrincipal()).getUserId();
        }

        throw new TokenInValidException("User is null");
    }

    public static final String getUserAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof IeqUser) {
            return ((IeqUser) authentication.getPrincipal()).getUsername();
        }

        throw new TokenInValidException("User is null");
    }

    public static final String getUserAccount(Authentication authentication) {
        if (authentication.getPrincipal() instanceof AuthUser) {
            return ((AuthUser) authentication.getPrincipal()).getUsername();
        }

        throw new TokenInValidException("User is null");
    }

    public static final String getApplicationCode() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof IeqUser) {
            String applicationCode = ((IeqUser) authentication.getPrincipal()).getApplicationCode();
            Preconditions.checkArgument(StringUtils.isNotBlank(applicationCode));
            return applicationCode;
        }

        throw new TokenInValidException("Application code is null or empty");
    }

    public static final boolean isSu() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AuthUser) {
            return ((AuthUser) authentication.getPrincipal()).isSu();
        }

        throw new TokenInValidException("Su flag is null");
    }

    public static final boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof IeqUser) {
            return ((IeqUser) authentication.getPrincipal()).isAdmin();
        }

        throw new TokenInValidException("Admin flag is null");
    }

}
