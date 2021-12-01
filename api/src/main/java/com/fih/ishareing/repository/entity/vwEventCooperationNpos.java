package com.fih.ishareing.repository.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the vw_eventCooperationNpos database table.
 * 
 */
@Entity
@Table(name = "vw_eventCooperationNpos")
public class vwEventCooperationNpos implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="event_id")
	private Integer eventId;

	@Column(name="npo_id")
	private Integer npoId;

	@Column(name="npoName")
	private String npoName;

	@ManyToOne
	@JoinColumn(name="event_id", insertable=false, updatable=false)
	private vwEvent event;

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

	public Integer getNpoId() {
		return npoId;
	}

	public void setNpoId(Integer npoId) {
		this.npoId = npoId;
	}

	public String getNpoName() {
		return npoName;
	}

	public void setNpoName(String npoName) {
		this.npoName = npoName;
	}

	public vwEvent getEvent() {
		return event;
	}

	public void setEvent(vwEvent event) {
		this.event = event;
	}
}