package com.fih.ishareing.service.auth.model;

public class UserAuthenticationVO {
    private String applicationCode;
    private String clientId;
    private String userCode;

    public UserAuthenticationVO() {
    }

    public UserAuthenticationVO(String applicationCode, String clientId, String userCode) {
        this.applicationCode = applicationCode;
        this.clientId = clientId;
        this.userCode = userCode;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}
