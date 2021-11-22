package com.fih.ishareing.service.user.model;

import com.fih.ishareing.repository.criteria.annotations.QueryCondition;

public class SimpleUserVO {
    private int id;
    private String code;
    private String account;
    private String name;
    private Boolean active;
    private Boolean enable;
    private Boolean validated;

    @QueryCondition(searchable = true, databaseField = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @QueryCondition(searchable = true, sortable = true, databaseField = "account")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @QueryCondition(searchable = true, sortable = true, databaseField = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @QueryCondition(searchable = true, sortable = true, databaseField = "active")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @QueryCondition(searchable = true, sortable = true, databaseField = "enable")
    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    @QueryCondition(searchable = true, sortable = true, databaseField = "validated")
    public Boolean getValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

}
