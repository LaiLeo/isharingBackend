package com.fih.ishareing.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * The persistent class for the vw_dumpTwmEvent database table.
 * 
 */
@Entity
@Table(name = "vw_dumpTwmEvent")
public class vwDumpTwmEvent implements Serializable {
	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "eventId")
	private Integer eventId;

	@Column(name = "userName")
	private String userName;

	@Column(name = "securityId")
	private String securityId;

	@Column(name = "name")
	private String name;

	@Column(name = "eventUid")
	private String eventUid;

	@Column(name = "eventSubject")
	private String eventSubject;

	@Column(name = "note")
	private String note;

	@Column(name = "eventDate")
	private String eventDate;

	@Column(name = "eventHour")
	private Integer eventHour;

	@Column(name="enterpriseSerialName")
	private String enterpriseSerialName;

	@Column(name="enterpriseSerialNumber")
	private String enterpriseSerialNumber;

	@Column(name="enterpriseSerialEmail")
	private String enterpriseSerialEmail;

	@Column(name="enterpriseSerialId")
	private String enterpriseSerialId;

	@Column(name="enterpriseSerialPhone")
	private String enterpriseSerialPhone;

	@Column(name="enterpriseSerialDepartment")
	private String enterpriseSerialDepartment;

	@Column(name="enterpriseSerialType")
	private String enterpriseSerialType;

	@Column(name="enterpriseSerialGroup")
	private String enterpriseSerialGroup;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEventUid() {
		return eventUid;
	}

	public void setEventUid(String eventUid) {
		this.eventUid = eventUid;
	}

	public String getEventSubject() {
		return eventSubject;
	}

	public void setEventSubject(String eventSubject) {
		this.eventSubject = eventSubject;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public Integer getEventHour() {
		return eventHour;
	}

	public void setEventHour(Integer eventHour) {
		this.eventHour = eventHour;
	}

	public String getEnterpriseSerialName() {
		return enterpriseSerialName;
	}

	public void setEnterpriseSerialName(String enterpriseSerialName) {
		this.enterpriseSerialName = enterpriseSerialName;
	}

	public String getEnterpriseSerialNumber() {
		return enterpriseSerialNumber;
	}

	public void setEnterpriseSerialNumber(String enterpriseSerialNumber) {
		this.enterpriseSerialNumber = enterpriseSerialNumber;
	}

	public String getEnterpriseSerialEmail() {
		return enterpriseSerialEmail;
	}

	public void setEnterpriseSerialEmail(String enterpriseSerialEmail) {
		this.enterpriseSerialEmail = enterpriseSerialEmail;
	}

	public String getEnterpriseSerialId() {
		return enterpriseSerialId;
	}

	public void setEnterpriseSerialId(String enterpriseSerialId) {
		this.enterpriseSerialId = enterpriseSerialId;
	}

	public String getEnterpriseSerialPhone() {
		return enterpriseSerialPhone;
	}

	public void setEnterpriseSerialPhone(String enterpriseSerialPhone) {
		this.enterpriseSerialPhone = enterpriseSerialPhone;
	}

	public String getEnterpriseSerialDepartment() {
		return enterpriseSerialDepartment;
	}

	public void setEnterpriseSerialDepartment(String enterpriseSerialDepartment) {
		this.enterpriseSerialDepartment = enterpriseSerialDepartment;
	}

	public String getEnterpriseSerialType() {
		return enterpriseSerialType;
	}

	public void setEnterpriseSerialType(String enterpriseSerialType) {
		this.enterpriseSerialType = enterpriseSerialType;
	}

	public String getEnterpriseSerialGroup() {
		return enterpriseSerialGroup;
	}

	public void setEnterpriseSerialGroup(String enterpriseSerialGroup) {
		this.enterpriseSerialGroup = enterpriseSerialGroup;
	}
}