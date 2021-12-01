package com.fih.ishareing.service.twm.model;

import javax.validation.constraints.NotNull;

public class TwmEnterpriseAddVO {
    @NotNull
    private String enterpriseSerialName;
    @NotNull
    private String enterpriseSerialNumber;
    @NotNull
    private String enterpriseSerialEmail;

    public String getEnterpriseSerialName() {
        return enterpriseSerialName;
    }

    public void setEnterpriseSerialName(String enterpriseSerialName) {
        this.enterpriseSerialName = enterpriseSerialName;
    }

    public String getEnterpriseSerialNumber() {
        return enterpriseSerialNumber;
    }

    public void setEnterpriseSerialNumber(String enterpriseSerialNumber) {
        this.enterpriseSerialNumber = enterpriseSerialNumber;
    }

    public String getEnterpriseSerialEmail() {
        return enterpriseSerialEmail;
    }

    public void setEnterpriseSerialEmail(String enterpriseSerialEmail) {
        this.enterpriseSerialEmail = enterpriseSerialEmail;
    }
}
