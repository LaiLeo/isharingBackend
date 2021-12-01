package com.fih.ishareing.service.user.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AuthUserUpdateVO {
    @Size(max = 75)
    @NotBlank(message = "must not be null or emtpy")
    private String username;

    @Size(max = 30)
    private String firstName;
    
    @Size(max = 30)
    private String lastName;
    
    @Size(max = 75)
    private String email;
    
    private Boolean isStaff;
    
    @Size(max = 50)
    private String name;
    
    @Size(max = 50)
    private String phone;
    
    @Size(max = 50)
    private String sercurityId;
    
    @Size(max = 9500)
    private String skillsDescription;
    
    private String birthday;
    
    @Size(max = 50)
    private String guardianName;
    
    @Size(max = 50)
    private String guardianPhone;
    
    private Boolean isPublic;

    @Size(max = 50)
    private String interest;
    
    @Size(max = 500)
    private String aboutMe;
    
    private Integer score;
    
    private Integer ranking;
    
    private Double eventHour;
    
    private Integer eventNum;
    
    private Double eventEnterpriseHour;
    
    private Double eventGeneralHour;

	@Size(max = 75)
	private String fubonAccount;

	@Size(max = 75)
	private String twmAccount;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Boolean getIsStaff() {
		return isStaff;
	}

	public void setIsStaff(Boolean isStaff) {
		this.isStaff = isStaff;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSercurityId() {
		return sercurityId;
	}

	public void setSercurityId(String sercurityId) {
		this.sercurityId = sercurityId;
	}

	public String getSkillsDescription() {
		return skillsDescription;
	}

	public void setSkillsDescription(String skillsDescription) {
		this.skillsDescription = skillsDescription;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGuardianName() {
		return guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	public String getGuardianPhone() {
		return guardianPhone;
	}

	public void setGuardianPhone(String guardianPhone) {
		this.guardianPhone = guardianPhone;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public Double getEventHour() {
		return eventHour;
	}

	public void setEventHour(Double eventHour) {
		this.eventHour = eventHour;
	}

	public Integer getEventNum() {
		return eventNum;
	}

	public void setEventNum(Integer eventNum) {
		this.eventNum = eventNum;
	}

	public Double getEventEnterpriseHour() {
		return eventEnterpriseHour;
	}

	public void setEventEnterpriseHour(Double eventEnterpriseHour) {
		this.eventEnterpriseHour = eventEnterpriseHour;
	}

	public Double getEventGeneralHour() {
		return eventGeneralHour;
	}

	public void setEventGeneralHour(Double eventGeneralHour) {
		this.eventGeneralHour = eventGeneralHour;
	}

	public Boolean getStaff() {
		return isStaff;
	}

	public void setStaff(Boolean staff) {
		isStaff = staff;
	}

	public Boolean getPublic() {
		return isPublic;
	}

	public void setPublic(Boolean aPublic) {
		isPublic = aPublic;
	}

	public String getFubonAccount() {
		return fubonAccount;
	}

	public void setFubonAccount(String fubonAccount) {
		this.fubonAccount = fubonAccount;
	}

	public String getTwmAccount() {
		return twmAccount;
	}

	public void setTwmAccount(String twmAccount) {
		this.twmAccount = twmAccount;
	}
}
