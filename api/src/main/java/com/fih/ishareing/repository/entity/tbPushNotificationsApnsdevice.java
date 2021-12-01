package com.fih.ishareing.repository.entity;

import javax.persistence.*;
import javax.xml.soap.SAAJResult;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the push_notifications_apnsdevice database table.
 * 
 */
@Entity
@Table(name = "push_notifications_apnsdevice")
public class tbPushNotificationsApnsdevice implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="name")
	private String name;

	@Column(name="active")
	private Boolean active;

	@Column(name="user_id")
	private Integer userId;

	@Column(name="device_id")
	private String deviceId;

	@Column(name="registration_id")
	private String registrationId;

	@Column(name="date_created")
	private Timestamp dateCreated;

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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public Timestamp getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}
}