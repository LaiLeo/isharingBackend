package com.fih.ishareing.service.user.model;

import java.util.Date;

import com.fih.ishareing.repository.criteria.annotations.QueryCondition;
import com.fih.ishareing.repository.criteria.annotations.QueryConditions;

public class UserVO extends SimpleUserVO {
    private String appCode;
    private String appName;
    private String email;
    private String phone;
    private UserRoleVO role;
    private Date modifiesAt;

    @QueryCondition(searchable = true, sortable = true, field = "appCode", databaseField = "applicationCode")
    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    @QueryCondition(searchable = true, sortable = true, field = "appName", databaseField = "applicationCode")
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @QueryCondition(searchable = true, sortable = true, databaseField = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @QueryCondition(searchable = true, sortable = true, databaseField = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @QueryConditions(value = {
            @QueryCondition(searchable = true, sortable = true, field = "role.name", databaseField = "roles.name") })
    public UserRoleVO getRole() {
        return role;
    }

    public void setRole(UserRoleVO role) {
        this.role = role;
    }

    @QueryCondition(searchable = true, sortable = true, databaseField = "modifiedTime")
    public Date getModifiesAt() {
        return modifiesAt;
    }

    public void setModifiesAt(Date modifiesAt) {
        this.modifiesAt = modifiesAt;
    }

}
