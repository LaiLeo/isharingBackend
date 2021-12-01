package com.fih.ishareing.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;



/**
 * The persistent class for the vw_userregisteredevent database table.
 * 
 */
@Entity
@Table(name = "vw_userregisteredevent")
public class vwUserRegisteredEvent implements Serializable {

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

	@Column(name="isJoined")
	private Boolean joined;

	@Column(name="score")
	private Integer score;

	@Column(name="registeredName")
	private String registeredName;

	@Column(name="registeredPhone")
	private String registeredPhone;

	@Column(name="registeredEmail")
	private String registeredEmail;

	@Column(name="registeredSkills")
	private String registeredSkills;

	@Column(name="registeredBirthday")
	private String registeredBirthday;

	@Column(name="registeredGuardianName")
	private String registeredGuardianName;

	@Column(name="guardianPhone")
	private String guardianPhone;

	@Column(name="securityId")
	private String securityId;

	@Column(name="note")
	private String note;

	@Column(name="employeeSerialNumber")
	private String employeeSerialNumber;

	@Column(name="enterpriseSerialNumber")
	private String enterpriseSerialNumber;

	@Column(name="isLeaved")
	private Boolean leaved;

	@Column(name="joinTime")
	private String joinTime;

	@Column(name="leaveTime")
	private String leaveTime;

	@Column(name="registerDate")
	private String registerDate;

	@Column(name="thirdEnterpriseSerialNumber")
	private String thirdEnterpriseSerialNumber;

	@Column(name="userScore")
	private Integer userScore;

	@Column(name="closeDate")
	private Timestamp closeDate;

	@Column(name="eventHour")
	private Integer eventHour;

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

	public Boolean getJoined() {
		return joined;
	}

	public void setJoined(Boolean joined) {
		this.joined = joined;
	}

	public Boolean getLeaved() {
		return leaved;
	}

	public void setLeaved(Boolean leaved) {
		this.leaved = leaved;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getRegisteredName() {
		return registeredName;
	}

	public void setRegisteredName(String registeredName) {
		this.registeredName = registeredName;
	}

	public String getRegisteredPhone() {
		return registeredPhone;
	}

	public void setRegisteredPhone(String registeredPhone) {
		this.registeredPhone = registeredPhone;
	}

	public String getRegisteredEmail() {
		return registeredEmail;
	}

	public void setRegisteredEmail(String registeredEmail) {
		this.registeredEmail = registeredEmail;
	}

	public String getRegisteredSkills() {
		return registeredSkills;
	}

	public void setRegisteredSkills(String registeredSkills) {
		this.registeredSkills = registeredSkills;
	}

	public String getRegisteredBirthday() {
		return registeredBirthday;
	}

	public void setRegisteredBirthday(String registeredBirthday) {
		this.registeredBirthday = registeredBirthday;
	}

	public String getRegisteredGuardianName() {
		return registeredGuardianName;
	}

	public void setRegisteredGuardianName(String registeredGuardianName) {
		this.registeredGuardianName = registeredGuardianName;
	}

	public String getGuardianPhone() {
		return guardianPhone;
	}

	public void setGuardianPhone(String guardianPhone) {
		this.guardianPhone = guardianPhone;
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getEmployeeSerialNumber() {
		return employeeSerialNumber;
	}

	public void setEmployeeSerialNumber(String employeeSerialNumber) {
		this.employeeSerialNumber = employeeSerialNumber;
	}

	public String getEnterpriseSerialNumber() {
		return enterpriseSerialNumber;
	}

	public void setEnterpriseSerialNumber(String enterpriseSerialNumber) {
		this.enterpriseSerialNumber = enterpriseSerialNumber;
	}

	public String getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}

	public String getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getThirdEnterpriseSerialNumber() {
		return thirdEnterpriseSerialNumber;
	}

	public void setThirdEnterpriseSerialNumber(String thirdEnterpriseSerialNumber) {
		this.thirdEnterpriseSerialNumber = thirdEnterpriseSerialNumber;
	}

	public Integer getUserScore() {
		return userScore;
	}

	public void setUserScore(Integer userScore) {
		this.userScore = userScore;
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

	public Integer getEventHour() {
		return eventHour;
	}

	public void setEventHour(Integer eventHour) {
		this.eventHour = eventHour;
	}
}