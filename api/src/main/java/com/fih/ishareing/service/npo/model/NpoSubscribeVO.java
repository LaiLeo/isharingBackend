package com.fih.ishareing.service.npo.model;

import javax.validation.constraints.NotNull;

public class NpoSubscribeVO {
    @NotNull
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
