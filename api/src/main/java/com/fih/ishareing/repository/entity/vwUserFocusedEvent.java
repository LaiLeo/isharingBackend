package com.fih.ishareing.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the vw_userfocusedevent database table.
 * 
 */
@Entity
@Table(name = "vw_userfocusedevent")
public class vwUserFocusedEvent implements Serializable {

	@Id
	@Column(name="id")
	private Integer id;
	
	@Column(name="userid")
	private Integer userId;
	
	@Column(name="eventid")
	private Integer eventID;
	
	@Column(name="npoid")
	private Integer npoID;
	
	@Column(name="image1")
	private String image1;
	
	@Column(name="uid")
	private String uid;

	@Column(name="pubDate")
	private Timestamp pubDate;

	@Column(name="happendate")
	private Timestamp happenDate;

	@Column(name="subject")
	private String subject;
	
	@Column(name="reqvolunteernumer")
	private Integer reqVolunteerNumer;
	
	@Column(name="currvolunteenumber")
	private Integer currVolunteeNumber;
	
	@Column(name="thumbpath")
	private String thumbPath;
	
	@Column(name="isregister")
	private Boolean register;
	
	@Column(name="isfocus")
	private Boolean focus;

	@Column(name="is_volunteer_event")
	private Boolean volunteerEvent;

	@Column(name="is_enterprise")
	private Boolean enterprise;

	@Column(name="focusedDate")
	private Timestamp focusedDate;

	@Column(name="closedDate")
	private Timestamp closedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getEventID() {
		return eventID;
	}

	public void setEventID(Integer eventID) {
		this.eventID = eventID;
	}

	public Integer getNpoID() {
		return npoID;
	}

	public void setNpoID(Integer npoID) {
		this.npoID = npoID;
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

	public Integer getReqVolunteerNumer() {
		return reqVolunteerNumer;
	}

	public void setReqVolunteerNumer(Integer reqVolunteerNumer) {
		this.reqVolunteerNumer = reqVolunteerNumer;
	}

	public Integer getCurrVolunteeNumber() {
		return currVolunteeNumber;
	}

	public void setCurrVolunteeNumber(Integer currVolunteeNumber) {
		this.currVolunteeNumber = currVolunteeNumber;
	}

	public String getThumbPath() {
		return thumbPath;
	}

	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}

	public Boolean getRegister() {
		return register;
	}

	public void setRegister(Boolean register) {
		this.register = register;
	}

	public Boolean getFocus() {
		return focus;
	}

	public void setFocus(Boolean focus) {
		this.focus = focus;
	}

	public Boolean getVolunteerEvent() {
		return volunteerEvent;
	}

	public void setVolunteerEvent(Boolean volunteerEvent) {
		this.volunteerEvent = volunteerEvent;
	}

	public Boolean getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Boolean enterprise) {
		this.enterprise = enterprise;
	}

	public Timestamp getPubDate() {
		return pubDate;
	}

	public void setPubDate(Timestamp pubDate) {
		this.pubDate = pubDate;
	}

	public Timestamp getFocusedDate() {
		return focusedDate;
	}

	public void setFocusedDate(Timestamp focusedDate) {
		this.focusedDate = focusedDate;
	}

	public Timestamp getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Timestamp closedDate) {
		this.closedDate = closedDate;
	}
}