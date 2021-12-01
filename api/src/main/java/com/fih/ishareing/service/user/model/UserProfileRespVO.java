package com.fih.ishareing.service.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserProfileRespVO {
	private Integer id;
	private String username;
	private String uid;
	private String aboutMe;
	private String skillsDescription;
	private String interest;
	@JsonProperty("isStaff")
	private Boolean staff;
	@JsonProperty("isNpo")
	private Boolean npo;
	private Integer npoId;
	private Integer score;
	private Integer ranking;
	private Integer eventNum;
	private Double eventEnterpriseHour;
	private Double eventGeneralHour;
	private String icon;
	@JsonProperty("isFubon")
	private Boolean fubon;
	@JsonProperty("isTwm")
	private Boolean twm;
	private String email;
	private String phone;
	@JsonProperty("isPublic")
	private Boolean isPublic;
	private String name;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public String getSkillsDescription() {
		return skillsDescription;
	}

	public void setSkillsDescription(String skillsDescription) {
		this.skillsDescription = skillsDescription;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public Boolean getStaff() {
		return this.staff;
	}

	public void setStaff(Boolean staff) {
		this.staff = staff;
	}

	public Boolean getNpo() {
		return this.npo;
	}

	public void setNpo(Boolean npo) {
		this.npo = npo;
	}

	public Integer getNpoId() {
		return npoId;
	}

	public void setNpoId(Integer npoId) {
		this.npoId = npoId;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public Integer getEventNum() {
		return eventNum;
	}

	public void setEventNum(Integer eventNum) {
		this.eventNum = eventNum;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Boolean getPublic() {
		return isPublic;
	}

	public void setPublic(Boolean aPublic) {
		isPublic = aPublic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
