package com.fih.ishareing.service.event.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class EventRegisteredUpdateVO {
    @NotNull
    private Integer userId;
    @NotNull
    private Integer eventId;

    @JsonProperty("isJoined")
    private Boolean joined;
    @JsonProperty("isLeaved")
    private Boolean leaved;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Boolean getJoined() {
        return joined;
    }

    public void setJoined(Boolean joined) {
        this.joined = joined;
    }

    public Boolean getLeaved() {
        return leaved;
    }

    public void setLeaved(Boolean leaved) {
        this.leaved = leaved;
    }
}
