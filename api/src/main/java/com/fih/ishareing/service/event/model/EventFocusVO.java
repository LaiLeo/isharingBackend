package com.fih.ishareing.service.event.model;

import javax.validation.constraints.NotNull;

public class EventFocusVO {
    @NotNull
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
