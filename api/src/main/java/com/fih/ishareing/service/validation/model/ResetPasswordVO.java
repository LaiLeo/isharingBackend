package com.fih.ishareing.service.validation.model;

public class ResetPasswordVO {
    private String accessCode;
    private String password;

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
