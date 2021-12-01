package com.fih.ishareing.service.notifications.model;

import com.fih.ishareing.repository.criteria.annotations.QueryCondition;

import java.sql.Timestamp;

public class NotificationsGcmVO {
    private Integer id;
    private String name;
    private Boolean active;
    private Integer userId;
    private Integer deviceId;
    private String registrationId;
    private Timestamp dataCreated;

    @QueryCondition(searchable = true, sortable = true, field = "id", databaseField = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @QueryCondition(searchable = true, sortable = true, field = "name", databaseField = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @QueryCondition(searchable = true, sortable = true, field = "active", databaseField = "active")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    @QueryCondition(searchable = true, sortable = false, field = "userId", databaseField = "userId")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    @QueryCondition(searchable = true, sortable = false, field = "deviceId", databaseField = "deviceId")
    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }
    @QueryCondition(searchable = true, sortable = true, field = "registrationId", databaseField = "registrationId")
    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }
    @QueryCondition(searchable = true, sortable = true, field = "dataCreated", databaseField = "dataCreated")
    public Timestamp getDataCreated() {
        return dataCreated;
    }

    public void setDataCreated(Timestamp dataCreated) {
        this.dataCreated = dataCreated;
    }
}
