package com.fih.ishareing.service.thirdLogin.model;

import javax.validation.constraints.NotNull;

public class TwmUserValidVO {
    @NotNull
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
