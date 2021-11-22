package com.fih.ishareing.service;

import com.fih.ishareing.service.auth.AuthenticationUtils;

public abstract class AbstractAuthenticationService {

    protected final int getUserId() {
        return AuthenticationUtils.getUserId();
    }

    protected final String getUserAccount() {
        return AuthenticationUtils.getUserAccount();
    }

    protected final String getApplicationCode() {
        return AuthenticationUtils.getApplicationCode();
    }

    protected final boolean isSu() {
        return AuthenticationUtils.isSu();
    }

    protected final boolean isAdmin() {
        return AuthenticationUtils.isAdmin();
    }
}
