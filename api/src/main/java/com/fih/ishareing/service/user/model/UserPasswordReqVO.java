package com.fih.ishareing.service.user.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserPasswordReqVO {
	
    @Size(max = 16)
    @NotBlank(message = "must not be null or emtpy")
    private String oldPassword;
	
    @Size(max = 16)
    @NotBlank(message = "must not be null or emtpy")
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
