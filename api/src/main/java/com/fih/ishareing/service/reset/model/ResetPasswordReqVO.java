package com.fih.ishareing.service.reset.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ResetPasswordReqVO {

    @Size(max = 32)
    @NotBlank(message = "must not be null or emtpy")
    private String accessCode;

    @Size(max = 16)
    @NotBlank(message = "must not be null or emtpy")
    private String password;

	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}