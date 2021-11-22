package com.fih.ishareing.service.auth.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class IeqAccessTokenVO {

    private String accessToken;
    private long expiresIn;
    private String refreshToken;
    private Boolean resetPassword;
    private Boolean isSu;
    private Boolean isAdmin;
    private String applicationCode;

    private IeqAccessTokenVO(Builder builder) {
        this.accessToken = builder.accessToken;
        this.expiresIn = builder.expiresIn;
        this.refreshToken = builder.refreshToken;
        this.resetPassword = builder.resetPassword;
        this.isSu = builder.isSu;
        this.isAdmin = builder.isAdmin;
        this.applicationCode = builder.applicationCode;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Boolean getResetPassword() {
        return resetPassword;
    }

    public void setResetPassword(Boolean resetPassword) {
        this.resetPassword = resetPassword;
    }

    @JsonProperty("isSu")
    public Boolean isSu() {
        return isSu;
    }

    public void setIsSu(Boolean isSu) {
        this.isSu = isSu;
    }

    @JsonProperty("isAdmin")
    public Boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }

    public static final class Builder {
        private String accessToken;
        private long expiresIn;
        private String refreshToken;
        private Boolean resetPassword;
        private Boolean isSu;
        private Boolean isAdmin;
        private String applicationCode;

        public Builder setAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder setExpiresIn(long expiresIn) {
            this.expiresIn = expiresIn;
            return this;
        }

        public Builder setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public Builder setResetPassword(Boolean resetPassword) {
            this.resetPassword = resetPassword;
            return this;
        }

        public Builder setIsSu(Boolean isSu) {
            this.isSu = isSu;
            return this;
        }

        public Builder setIsAdmin(Boolean isAdmin) {
            this.isAdmin = isAdmin;
            return this;
        }

        public Builder setApplicationCode(String applicationCode) {
            this.applicationCode = applicationCode;
            return this;
        }

        public IeqAccessTokenVO build() {
            return new IeqAccessTokenVO(this);
        }
    }

}