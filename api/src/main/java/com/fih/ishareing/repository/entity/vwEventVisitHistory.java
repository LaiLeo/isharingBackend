package com.fih.ishareing.repository.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the vw_eventvisithistory database table.
 * 
 */
@Entity
@Table(name = "vw_eventvisithistory")
public class vwEventVisitHistory implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="userid")
	private Integer userId;
	
	@Column(name="eventid")
	private Integer eventID;

	@Column(name="visitTime")
	private Timestamp visitTime;
	
	@Column(name="npoid")
	private Integer npoID;
	
	@Column(name="image1")
	private String image1;
	
	@Column(name="uid")
	private String uid;
	
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

	@Column(name="closeDate")
	private Timestamp closeDate;

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

	public Timestamp getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Timestamp closeDate) {
		this.closeDate = closeDate;
	}

	public Timestamp getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Timestamp visitTime) {
		this.visitTime = visitTime;
	}
}