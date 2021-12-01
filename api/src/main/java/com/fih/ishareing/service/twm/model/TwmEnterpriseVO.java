package com.fih.ishareing.service.twm.model;

import com.fih.ishareing.repository.criteria.annotations.QueryCondition;

public class TwmEnterpriseVO {
    private Integer id;
    private String enterpriseSerialName;
    private String enterpriseSerialNumber;
    private String enterpriseSerialEmail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEnterpriseSerialName() {
        return enterpriseSerialName;
    }

    public void setEnterpriseSerialName(String enterpriseSerialName) {
        this.enterpriseSerialName = enterpriseSerialName;
    }

    @QueryCondition(searchable = true, sortable = true, field = "enterpriseSerialNumber", databaseField = "enterpriseSerialNumber")
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
