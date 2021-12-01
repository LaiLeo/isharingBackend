package com.fih.ishareing.service.reset.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ResetPermissionReqVO {

    @Size(max = 75)
    @NotBlank(message = "must not be null or emtpy")
    private String email;

    @Size(max = 256)
    @NotBlank(message = "must not be null or emtpy")
    private String accessPage;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccessPage() {
		return accessPage;
	}

	public void setAccessPage(String accessPage) {
		this.accessPage = accessPage;
	}


}