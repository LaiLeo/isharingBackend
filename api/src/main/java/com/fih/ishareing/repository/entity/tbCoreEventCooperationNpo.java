package com.fih.ishareing.repository.entity;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the core_event_cooperation_NPO database table.
 * 
 */
@Entity
@Table(name = "core_event_cooperation_NPO")
public class tbCoreEventCooperationNpo implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="event_id")
	private Integer eventId;

	@Column(name="npo_id")
	private Integer npoId;

	public tbCoreEventCooperationNpo() {
	}

	public tbCoreEventCooperationNpo(Integer eventId, Integer npoId) {
		this.eventId = eventId;
		this.npoId = npoId;
	}

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
}