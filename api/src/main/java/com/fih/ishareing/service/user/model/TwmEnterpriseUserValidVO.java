package com.fih.ishareing.service.user.model;

import javax.validation.constraints.NotNull;

public class TwmEnterpriseUserValidVO {
    @NotNull
    private String enterpriseSerialNumber;
    @NotNull
    private String enterpriseSerialEmail;
    @NotNull
    private String enterpriseSerialDepartment;
    @NotNull
    private String enterpriseSerialName;

    private String enterpriseSerialCode;
    @NotNull
    private String enterpriseSerialId;
    @NotNull
    private String enterpriseSerialPhone;
    @NotNull
    private String enterpriseSerialType;
    @NotNull
    private String enterpriseSerialGroup;

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

    public String getEnterpriseSerialDepartment() {
        return enterpriseSerialDepartment;
    }

    public void setEnterpriseSerialDepartment(String enterpriseSerialDepartment) {
        this.enterpriseSerialDepartment = enterpriseSerialDepartment;
    }

    public String getEnterpriseSerialName() {
        return enterpriseSerialName;
    }

    public void setEnterpriseSerialName(String enterpriseSerialName) {
        this.enterpriseSerialName = enterpriseSerialName;
    }

    public String getEnterpriseSerialCode() {
        return enterpriseSerialCode;
    }

    public void setEnterpriseSerialCode(String enterpriseSerialCode) {
        this.enterpriseSerialCode = enterpriseSerialCode;
    }

    public String getEnterpriseSerialId() {
        return enterpriseSerialId;
    }

    public void setEnterpriseSerialId(String enterpriseSerialId) {
        this.enterpriseSerialId = enterpriseSerialId;
    }

    public String getEnterpriseSerialPhone() {
        return enterpriseSerialPhone;
    }

    public void setEnterpriseSerialPhone(String enterpriseSerialPhone) {
        this.enterpriseSerialPhone = enterpriseSerialPhone;
    }

    public String getEnterpriseSerialType() {
        return enterpriseSerialType;
    }

    public void setEnterpriseSerialType(String enterpriseSerialType) {
        this.enterpriseSerialType = enterpriseSerialType;
    }

    public String getEnterpriseSerialGroup() {
        return enterpriseSerialGroup;
    }

    public void setEnterpriseSerialGroup(String enterpriseSerialGroup) {
        this.enterpriseSerialGroup = enterpriseSerialGroup;
    }
}
