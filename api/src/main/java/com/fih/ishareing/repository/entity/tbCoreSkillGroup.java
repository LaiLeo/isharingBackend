package com.fih.ishareing.repository.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the core_skillgroup database table.
 * 
 */
@Entity
@Table(name = "core_skillgroup")
public class tbCoreSkillGroup implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="owner_event_id")
	private Integer ownerEventId;

	@Column(name="name")
	private String name;

	@Column(name="skills_description")
	private String skillsDescription;

	@Column(name="volunteer_number")
	private Integer volunteerNumber;

	@Column(name="current_volunteer_number")
	private Integer currentVolunteerNumber;

	@ManyToOne
	@JoinColumn(name="owner_event_id", insertable=false, updatable=false)
	private vwEvent event;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOwnerEventId() {
		return ownerEventId;
	}

	public void setOwnerEventId(Integer ownerEventId) {
		this.ownerEventId = ownerEventId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSkillsDescription() {
		return skillsDescription;
	}

	public void setSkillsDescription(String skillsDescription) {
		this.skillsDescription = skillsDescription;
	}

	public Integer getVolunteerNumber() {
		return volunteerNumber;
	}

	public void setVolunteerNumber(Integer volunteerNumber) {
		this.volunteerNumber = volunteerNumber;
	}

	public Integer getCurrentVolunteerNumber() {
		return currentVolunteerNumber;
	}

	public void setCurrentVolunteerNumber(Integer currentVolunteerNumber) {
		this.currentVolunteerNumber = currentVolunteerNumber;
	}

	public vwEvent getEvent() {
		return event;
	}

	public void setEvent(vwEvent event) {
		this.event = event;
	}
}