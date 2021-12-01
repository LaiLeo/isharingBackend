package com.fih.ishareing.repository.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the core_pushmessage database table.
 * 
 */
@Entity
@Table(name = "core_pushmessage")
public class tbPushMessage implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="user_id")
	private Integer userId;

	@Column(name="subject")
	private String subject;

	@Column(name="content")
	private String content;

	@Column(name="response_num")
	private Integer responseNum;

	@Column(name="broadcast_time")
	private Timestamp broadcastTime;

	@Column(name="event_id")
	private Integer eventId;

	@Column(name="page_type")
	private String pageType;

	@Column(name="donation_npo_id")
	private Timestamp donationNpoId;

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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getResponseNum() {
		return responseNum;
	}

	public void setResponseNum(Integer responseNum) {
		this.responseNum = responseNum;
	}

	public Timestamp getBroadcastTime() {
		return broadcastTime;
	}

	public void setBroadcastTime(Timestamp broadcastTime) {
		this.broadcastTime = broadcastTime;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public Timestamp getDonationNpoId() {
		return donationNpoId;
	}

	public void setDonationNpoId(Timestamp donationNpoId) {
		this.donationNpoId = donationNpoId;
	}
}