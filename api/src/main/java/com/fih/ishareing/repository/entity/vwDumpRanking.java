package com.fih.ishareing.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * The persistent class for the vw_dumpRanking database table.
 * 
 */
@Entity
@Table(name = "vw_dumpRanking")
public class vwDumpRanking implements Serializable {
	@Id
	@Column(name="id")
	private Integer id;

	@Column(name="email")
	private String email;

	@Column(name="ranking")
	private Integer ranking;

	@Column(name="name")
	private String name;

	@Column(name="phone")
	private String phone;

	@Column(name="eventNames")
	private String eventNames;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
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

	public String getEventNames() {
		return eventNames;
	}

	public void setEventNames(String eventNames) {
		this.eventNames = eventNames;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}