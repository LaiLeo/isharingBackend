package com.fih.ishareing.service.auth.model;

import javax.validation.constraints.NotBlank;

public class RefreshTokenVO {

    @NotBlank(message = "must not be null or emtpy")
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
