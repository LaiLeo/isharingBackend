package com.fih.ishareing.service.user.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AuthUserAddVO {
    @Size(max = 75)
    @NotBlank(message = "must not be null or emtpy")
    private String username;

    @Size(max = 128)
    @NotBlank(message = "must not be null or emtpy")
    private String password;

    @Size(max = 30)
    @NotBlank(message = "must not be null or emtpy")
    private String firstName;

    @Size(max = 30)
    @NotBlank(message = "must not be null or emtpy")
    private String lastName;

    @Size(max = 75)
    @NotBlank(message = "must not be null or emtpy")
    private String email;

    private Boolean isSuperuser;

    private Boolean isStaff;

	private String accessCode;
    
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getIsSuperuser() {
		return isSuperuser;
	}
	public void setIsSuperuser(Boolean isSuperuser) {
		this.isSuperuser = isSuperuser;
	}
	public Boolean getIsStaff() {
		return isStaff;
	}
	public void setIsStaff(Boolean isStaff) {
		this.isStaff = isStaff;
	}

	public Boolean getSuperuser() {
		return isSuperuser;
	}

	public void setSuperuser(Boolean superuser) {
		isSuperuser = superuser;
	}

	public Boolean getStaff() {
		return isStaff;
	}

	public void setStaff(Boolean staff) {
		isStaff = staff;
	}

	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}
}
