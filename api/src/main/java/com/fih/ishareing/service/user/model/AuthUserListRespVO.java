package com.fih.ishareing.service.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AuthUserListRespVO {

	private Integer id;
	private Boolean isActive;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	@JsonProperty("isStaff")
	private String staff;
	private Timestamp lastLogin;
	private Timestamp dateJoined;
	@JsonProperty("isValid")
	private Boolean valid;
	private String uid;
	private String photo;
	private String name;
	private String phone;
	private String securityId;
	private String skillsDescription;
	private String birthday;
	private String guardianName;
	private String guardianPhone;
	private String icon;
	private String thumbPath;
	@JsonProperty("isPublic")
	private Boolean isPublic;
	private String interest;
	private String aboutMe;
	private Integer score;
	private Integer ranking;
	private Double eventHour;
	private Integer eventNum;
	private Double eventEnterpriseHour;
	private Double eventGeneralHour;
	@JsonProperty("isFubon")
	private Boolean fubon;
	@JsonProperty("isTwm")
	private Boolean twm;

	private List<UserLicenseImagesVO> licenseImages;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getActive() {
		return isActive;
	}

	public void setActive(Boolean active) {
		isActive = active;
	}

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

	public String getStaff() {
		return staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}

	public Timestamp getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Timestamp getDateJoined() {
		return dateJoined;
	}

	public void setDateJoined(Timestamp dateJoined) {
		this.dateJoined = dateJoined;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
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

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getThumbPath() {
		return thumbPath;
	}

	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}

	public Boolean getPublic() {
		return isPublic;
	}

	public void setPublic(Boolean aPublic) {
		isPublic = aPublic;
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

	public Boolean getFubon() {
		return fubon;
	}

	public void setFubon(Boolean fubon) {
		this.fubon = fubon;
	}

	public Boolean getTwm() {
		return twm;
	}

	public void setTwm(Boolean twm) {
		this.twm = twm;
	}

	public List<UserLicenseImagesVO> getLicenseImages() {
		return licenseImages;
	}

	public void setLicenseImages(List<UserLicenseImagesVO> licenseImages) {
		this.licenseImages = licenseImages;
	}
}
