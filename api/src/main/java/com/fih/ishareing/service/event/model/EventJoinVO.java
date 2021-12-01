package com.fih.ishareing.service.event.model;

import javax.validation.constraints.NotNull;

public class EventJoinVO {
    @NotNull
    private String uid;
    private Integer userId;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
