package com.fih.ishareing.service.auth.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginVO {

    @Size(max = 32)
    @NotBlank(message = "must not be null or emtpy")
    private String account;

    @Size(max = 256)
    @NotBlank(message = "must not be null or emtpy")
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}