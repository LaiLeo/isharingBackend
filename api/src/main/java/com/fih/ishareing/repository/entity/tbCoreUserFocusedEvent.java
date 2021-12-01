package com.fih.ishareing.repository.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the core_userfocusedevent database table.
 * 
 */
@Entity
@Table(name = "core_userfocusedevent")
public class tbCoreUserFocusedEvent implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="focused_event_id")
	private Integer focusedEventId;

	@Column(name="focusedDate")
	private Timestamp focusedDate;

	public tbCoreUserFocusedEvent() {
	}

	public tbCoreUserFocusedEvent(Integer userId, Integer focusedEventId) {
		this.userId = userId;
		this.focusedEventId = focusedEventId;
	}

	public tbCoreUserFocusedEvent(Integer userId, Integer focusedEventId, Timestamp focusedDate) {
		this.userId = userId;
		this.focusedEventId = focusedEventId;
		this.focusedDate = focusedDate;
	}

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

	public Integer getFocusedEventId() {
		return focusedEventId;
	}

	public void setFocusedEventId(Integer focusedEventId) {
		this.focusedEventId = focusedEventId;
	}

	public Timestamp getFocusedDate() {
		return focusedDate;
	}

	public void setFocusedDate(Timestamp focusedDate) {
		this.focusedDate = focusedDate;
	}
}