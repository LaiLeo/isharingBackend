package com.fih.ishareing.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * The persistent class for the vw_dumpFubonEvent database table.
 * 
 */
@Entity
@Table(name = "vw_dumpFubonEvent")
public class vwDumpFubonEvent implements Serializable {
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
}