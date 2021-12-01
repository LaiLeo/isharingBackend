package com.fih.ishareing.service.event.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fih.ishareing.repository.criteria.annotations.QueryCondition;

import java.sql.Timestamp;

public class EventRegisteredVO {
	private Integer userId;
	private Integer eventId;
	private Integer score;
	private String uid;
	private String name;
	private String phone;
	private String email;
	private String skills;
	private String birthday;
	private String guardianName;
	private String guardianPhone;
	private String securityId;
	private String note;
	private String employeeSerialNumber;
	private String enterpriseSerialNumber;
	@JsonProperty("isJoined")
	private Boolean joined;
	@JsonProperty("isLeaved")
	private Boolean leaved;
	private Timestamp joinTime;
	private Timestamp leaveTime;
	private Timestamp registerDate;
	private String userEnterpriseSerialNumber;
	private Integer userScore;
	@JsonProperty("isVolunteerEvent")
	private Boolean volunteerEvent;
	@JsonProperty("isEnterprise")
	private Boolean enterprise;

	@QueryCondition(searchable = true, sortable = true, field = "userId", databaseField = "userId")
	public Integer getUserId() {
		return userId;
	}
	@QueryCondition(searchable = true, sortable = true, field = "eventId", databaseField = "eventID")
	public Integer getEventId() {
		return eventId;
	}
	@QueryCondition(searchable = true, sortable = true, field = "score", databaseField = "score")
	public Integer getScore() {
		return score;
	}
	@QueryCondition(searchable = true, sortable = true, field = "name", databaseField = "registeredName")
	public String getName() {
		return name;
	}
	@QueryCondition(searchable = true, sortable = true, field = "phone", databaseField = "registeredPhone")
	public String getPhone() {
		return phone;
	}
	@QueryCondition(searchable = true, sortable = true, field = "email", databaseField = "registeredEmail")
	public String getEmail() {
		return email;
	}

	public String getSkills() {
		return skills;
	}
	@QueryCondition(searchable = true, sortable = true, field = "birthday", databaseField = "registeredBirthday")
	public String getBirthday() {
		return birthday;
	}
	@QueryCondition(searchable = true, sortable = true, field = "guardianName", databaseField = "registeredGuardianName")
	public String getGuardianName() {
		return guardianName;
	}
	@QueryCondition(searchable = true, sortable = true, field = "guardianPhone", databaseField = "guardianPhone")
	public String getGuardianPhone() {
		return guardianPhone;
	}
	@QueryCondition(searchable = true, sortable = true, field = "securityId", databaseField = "securityId")
	public String getSecurityId() {
		return securityId;
	}

	public String getNote() {
		return note;
	}
	@QueryCondition(searchable = true, sortable = true, field = "employeeSerialNumber", databaseField = "employeeSerialNumber")
	public String getEmployeeSerialNumber() {
		return employeeSerialNumber;
	}
	@QueryCondition(searchable = true, sortable = true, field = "enterpriseSerialNumber", databaseField = "enterpriseSerialNumber")
	public String getEnterpriseSerialNumber() {
		return enterpriseSerialNumber;
	}
	@QueryCondition(searchable = true, sortable = true, field = "isJoined", databaseField = "joined")
	public Boolean getJoined() {
		return this.joined;
	}
	@QueryCondition(searchable = true, sortable = true, field = "isLeaved", databaseField = "leaved")
	public Boolean getLeaved() {
		return this.leaved;
	}
	@QueryCondition(searchable = true, sortable = true, field = "joinTime", databaseField = "joinTime")
	public Timestamp getJoinTime() {
		return joinTime;
	}
	@QueryCondition(searchable = true, sortable = true, field = "leaveTime", databaseField = "leaveTime")
	public Timestamp getLeaveTime() {
		return leaveTime;
	}
	@QueryCondition(searchable = true, sortable = true, field = "registerDate", databaseField = "registerDate")
	public Timestamp getRegisterDate() {
		return registerDate;
	}

	public String getUserEnterpriseSerialNumber() {
		return userEnterpriseSerialNumber;
	}
	@QueryCondition(searchable = true, sortable = false, field = "userScore", databaseField = "userScore")
	public Integer getUserScore() {
		return userScore;
	}
	@QueryCondition(searchable = true, sortable = true, field = "isVolunteerEvent", databaseField = "volunteerEvent")
	public Boolean getVolunteerEvent() {
		return this.volunteerEvent;
	}
	@QueryCondition(searchable = true, sortable = false, field = "isEnterprise", databaseField = "enterprise")
	public Boolean getEnterprise() {
		return this.enterprise;
	}

	@QueryCondition(searchable = true, sortable = false, field = "uid", databaseField = "uid")
	public String getUid() {
		return uid;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	public void setGuardianPhone(String guardianPhone) {
		this.guardianPhone = guardianPhone;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setEmployeeSerialNumber(String employeeSerialNumber) {
		this.employeeSerialNumber = employeeSerialNumber;
	}

	public void setEnterpriseSerialNumber(String enterpriseSerialNumber) {
		this.enterpriseSerialNumber = enterpriseSerialNumber;
	}

	public void setJoined(Boolean joined) {
		this.joined = joined;
	}

	public void setLeaved(Boolean leaved) {
		this.leaved = leaved;
	}

	public void setJoinTime(Timestamp joinTime) {
		this.joinTime = joinTime;
	}

	public void setLeaveTime(Timestamp leaveTime) {
		this.leaveTime = leaveTime;
	}

	public void setRegisterDate(Timestamp registerDate) {
		this.registerDate = registerDate;
	}

	public void setUserEnterpriseSerialNumber(String userEnterpriseSerialNumber) {
		this.userEnterpriseSerialNumber = userEnterpriseSerialNumber;
	}

	public void setUserScore(Integer userScore) {
		this.userScore = userScore;
	}

	public void setVolunteerEvent(Boolean volunteerEvent) {
		this.volunteerEvent = volunteerEvent;
	}

	public void setEnterprise(Boolean enterprise) {
		this.enterprise = enterprise;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
}
