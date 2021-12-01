package com.fih.ishareing.repository.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the core_usersubscribednpo database table.
 * 
 */
@Entity
@Table(name = "core_usersubscribednpo")
public class tbCoreUserSubscribedNpo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="subscribed_NPO_id")
	private Integer subscribedNpoId;

	@Column(name="focusedDate")
	private Timestamp focusedDate;

	public tbCoreUserSubscribedNpo() {
	}

	public tbCoreUserSubscribedNpo(Integer userId, Integer subscribedNpoId) {
		this.userId = userId;
		this.subscribedNpoId = subscribedNpoId;
	}

	public tbCoreUserSubscribedNpo(Integer userId, Integer subscribedNpoId, Timestamp focusedDate) {
		this.userId = userId;
		this.subscribedNpoId = subscribedNpoId;
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

	public Integer getSubscribedNpoId() {
		return subscribedNpoId;
	}

	public void setSubscribedNpoId(Integer subscribedNpoId) {
		this.subscribedNpoId = subscribedNpoId;
	}

	public Timestamp getFocusedDate() {
		return focusedDate;
	}

	public void setFocusedDate(Timestamp focusedDate) {
		this.focusedDate = focusedDate;
	}
}