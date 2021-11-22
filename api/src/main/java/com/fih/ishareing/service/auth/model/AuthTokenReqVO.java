package com.fih.ishareing.service.auth.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AuthTokenReqVO {

    @Size(max = 100)
    @NotBlank(message = "must not be null or emtpy")
    private String username;

    @Size(max = 128)
    @NotBlank(message = "must not be null or emtpy")
    private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}