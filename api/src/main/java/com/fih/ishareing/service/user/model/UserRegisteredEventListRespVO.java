package com.fih.ishareing.service.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRegisteredEventListRespVO {

	private Integer id;
	private Integer npoId;
	private String image1;
	private String uid;
	private Timestamp pubDate;
	private Timestamp happenDate;
	private String subject;
	private Integer requiredVolunteerNum;
	private Integer currentVolunteerNum;
	private String thumbPath;
	private Boolean isRegistered;
	private Boolean isFocus;
	@JsonProperty("isVolunteerEvent")
	private Boolean volunteerEvent;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getNpoId() {
		return npoId;
	}
	public void setNpoId(Integer npoId) {
		this.npoId = npoId;
	}
	public String getImage1() {
		return image1;
	}
	public void setImage1(String image1) {
		this.image1 = image1;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Timestamp getHappenDate() {
		return happenDate;
	}
	public void setHappenDate(Timestamp happenDate) {
		this.happenDate = happenDate;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Integer getRequiredVolunteerNum() {
		return requiredVolunteerNum;
	}
	public void setRequiredVolunteerNum(Integer requiredVolunteerNum) {
		this.requiredVolunteerNum = requiredVolunteerNum;
	}
	public Integer getCurrentVolunteerNum() {
		return currentVolunteerNum;
	}
	public void setCurrentVolunteerNum(Integer currentVolunteerNum) {
		this.currentVolunteerNum = currentVolunteerNum;
	}
	public String getThumbPath() {
		return thumbPath;
	}
	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}
	public Boolean getIsRegistered() {
		return isRegistered;
	}
	public void setIsRegistered(Boolean isRegistered) {
		this.isRegistered = isRegistered;
	}
	public Boolean getIsFocus() {
		return isFocus;
	}
	public void setIsFocus(Boolean isFocus) {
		this.isFocus = isFocus;
	}

	public Timestamp getPubDate() {
		return pubDate;
	}

	public void setPubDate(Timestamp pubDate) {
		this.pubDate = pubDate;
	}

	public Boolean getVolunteerEvent() {
		return volunteerEvent;
	}

	public void setVolunteerEvent(Boolean volunteerEvent) {
		this.volunteerEvent = volunteerEvent;
	}
}
