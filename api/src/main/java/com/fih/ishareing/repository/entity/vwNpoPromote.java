package com.fih.ishareing.repository.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the vw_npoPromote database table.
 * 
 */
@Entity
@Table(name = "vw_npoPromote")
public class vwNpoPromote implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="name")
	private String name;

	@Column(name="uid")
	private String uid;

	@Column(name="promote")
	private Boolean promote;

	@Column(name="eventId")
	private Integer eventId;

	@Column(name="eventSubject")
	private String eventSubject;

	@Column(name="eventUid")
	private String eventUid;

	@Column(name="eventHappenDate")
	private Timestamp eventHappenDate;

	@Column(name="eventRequiredVolunteerNumber")
	private Integer eventRequiredVolunteerNumber;

	@Column(name="eventCurrentVolunteerNumber")
	private Integer eventCurrentVolunteerNumber;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Boolean getPromote() {
		return promote;
	}

	public void setPromote(Boolean promote) {
		this.promote = promote;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getEventSubject() {
		return eventSubject;
	}

	public void setEventSubject(String eventSubject) {
		this.eventSubject = eventSubject;
	}

	public String getEventUid() {
		return eventUid;
	}

	public void setEventUid(String eventUid) {
		this.eventUid = eventUid;
	}

	public Timestamp getEventHappenDate() {
		return eventHappenDate;
	}

	public void setEventHappenDate(Timestamp eventHappenDate) {
		this.eventHappenDate = eventHappenDate;
	}

	public Integer getEventRequiredVolunteerNumber() {
		return eventRequiredVolunteerNumber;
	}

	public void setEventRequiredVolunteerNumber(Integer eventRequiredVolunteerNumber) {
		this.eventRequiredVolunteerNumber = eventRequiredVolunteerNumber;
	}

	public Integer getEventCurrentVolunteerNumber() {
		return eventCurrentVolunteerNumber;
	}

	public void setEventCurrentVolunteerNumber(Integer eventCurrentVolunteerNumber) {
		this.eventCurrentVolunteerNumber = eventCurrentVolunteerNumber;
	}
}