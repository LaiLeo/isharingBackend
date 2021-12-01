package com.fih.ishareing.service.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class UserFocusedEventListRespVO {

	private Integer id;
	private Integer npoId;
	private String image1;
	private String uid;
	private Timestamp pubDate;
	private Timestamp happenDate;
	private Timestamp closeDate;
	private String subject;
	private Integer requiredVolunteerNum;
	private Integer currentVolunteerNum;
	private String thumbPath;
	@JsonProperty(value = "isRegistered")
	private Boolean registered;
	@JsonProperty(value = "isFocus")
	private Boolean focus;
	@JsonProperty(value = "isEnterprise")
	private Boolean enterprise;
	private Timestamp focusedDate;
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
	public Timestamp getPubDate() {
		return pubDate;
	}

	public void setPubDate(Timestamp pubDate) {
		this.pubDate = pubDate;
	}

	public Timestamp getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Timestamp closeDate) {
		this.closeDate = closeDate;
	}

	public Timestamp getFocusedDate() {
		return focusedDate;
	}

	public void setFocusedDate(Timestamp focusedDate) {
		this.focusedDate = focusedDate;
	}

	public Boolean getRegistered() {
		return registered;
	}

	public void setRegistered(Boolean registered) {
		this.registered = registered;
	}

	public Boolean getFocus() {
		return focus;
	}

	public void setFocus(Boolean focus) {
		this.focus = focus;
	}

	public Boolean getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Boolean enterprise) {
		this.enterprise = enterprise;
	}

	public Boolean getVolunteerEvent() {
		return volunteerEvent;
	}

	public void setVolunteerEvent(Boolean volunteerEvent) {
		this.volunteerEvent = volunteerEvent;
	}
}
