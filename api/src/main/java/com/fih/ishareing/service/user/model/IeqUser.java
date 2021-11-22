package com.fih.ishareing.service.user.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class IeqUser extends User {

    private static final long serialVersionUID = -5552778393266123237L;

    private int userId;
    private String applicationCode;
    private boolean isSu;
    private boolean isAdmin;

    private boolean forceResetPassword;

    public IeqUser(int userId, String applicationCode, boolean isSu, boolean isAdmin, boolean forceResetPassword,
            String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
        this.applicationCode = applicationCode;
        this.isSu = isSu;
        this.isAdmin = isAdmin;
        this.forceResetPassword = forceResetPassword;
    }

    public IeqUser(int userId, String applicationCode, boolean isSu, boolean isAdmin, boolean forceResetPassword,
            String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
            boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
        this.applicationCode = applicationCode;
        this.isSu = isSu;
        this.isAdmin = isAdmin;
        this.forceResetPassword = forceResetPassword;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }

    public boolean isSu() {
        return isSu;
    }

    public void setSu(boolean isSu) {
        this.isSu = isSu;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isForceResetPassword() {
        return forceResetPassword;
    }

    public void setForceResetPassword(boolean forceResetPassword) {
        this.forceResetPassword = forceResetPassword;
    }

}