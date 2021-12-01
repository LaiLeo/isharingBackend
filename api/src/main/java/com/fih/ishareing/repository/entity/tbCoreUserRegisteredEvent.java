package com.fih.ishareing.repository.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;



/**
 * The persistent class for the core_userregisteredevent database table.
 * 
 */
@Entity
@Table(name = "core_userregisteredevent")
public class tbCoreUserRegisteredEvent implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="registered_event_id")
	private Integer registeredEventId;
	
	@Column(name="isjoined")
	private Integer isJoined;
	
	@Column(name="score")
	private Integer score;

	@Column(name="name")
	private String name;

	@Column(name="phone")
	private String phone;
	
	@Column(name="email")
	private String email;
	
	@Column(name="skills")
	private String skills;

	@Column(name="birthday")
	private String birthday;

	@Column(name="guardian_name")
	private String guardianName;

	@Column(name="guardian_phone")
	private String guardianPhone;

	@Column(name="event_skill_group_id")
	private Integer eventSkillGroupId;

	@Column(name="security_id")
	private String securityId;

	@Column(name="join_time")
	private Timestamp joinTime;

	@Column(name="leave_time")
	private Timestamp leaveTime;

	@Column(name="isLeaved")
	private Integer isLeaved;

	@Column(name="note")
	private String note;

	@Column(name="register_date")
	private String registerDate;

	@Column(name="employee_serial_number")
	private String employeeSerialNumber;

	@Column(name="enterprise_serial_number")
	private String enterpriseSerialNumber;

	@Column(name="event_skill_group_list")
	private String eventSkillGroupList;

	@OneToOne(cascade = CascadeType.REFRESH)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "id", referencedColumnName = "registeredEventId", nullable = true, insertable = false, updatable = false)
	private vwEvent vwEvent;

	@OneToOne(cascade = CascadeType.REFRESH)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "id", referencedColumnName = "userId", nullable = true, insertable = false, updatable = false)
	private vwEvent vwUsers;

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

	public Integer getRegisteredEventId() {
		return registeredEventId;
	}

	public void setRegisteredEventId(Integer registeredEventId) {
		this.registeredEventId = registeredEventId;
	}

	public Integer getIsJoined() {
		return isJoined;
	}

	public void setIsJoined(Integer isJoined) {
		this.isJoined = isJoined;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
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

	public Integer getEventSkillGroupId() {
		return eventSkillGroupId;
	}

	public void setEventSkillGroupId(Integer eventSkillGroupId) {
		this.eventSkillGroupId = eventSkillGroupId;
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public Timestamp getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(Timestamp joinTime) {
		this.joinTime = joinTime;
	}

	public Timestamp getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Timestamp leaveTime) {
		this.leaveTime = leaveTime;
	}

	public Integer getIsLeaved() {
		return isLeaved;
	}

	public void setIsLeaved(Integer isLeaved) {
		this.isLeaved = isLeaved;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
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

	public String getEventSkillGroupList() {
		return eventSkillGroupList;
	}

	public void setEventSkillGroupList(String eventSkillGroupList) {
		this.eventSkillGroupList = eventSkillGroupList;
	}

	public vwEvent getVwEvent() {
		return vwEvent;
	}

	public void setVwEvent(vwEvent vwEvent) {
		this.vwEvent = vwEvent;
	}

	public vwEvent getVwUsers() {
		return vwUsers;
	}

	public void setVwUsers(vwEvent vwUsers) {
		this.vwUsers = vwUsers;
	}


}