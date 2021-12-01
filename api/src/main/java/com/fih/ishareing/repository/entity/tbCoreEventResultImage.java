package com.fih.ishareing.repository.entity;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the core_eventResultImage database table.
 * 
 */
@Entity
@Table(name = "core_eventresultimage")
public class tbCoreEventResultImage implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="event_id")
	private Integer eventId;

	@Column(name="image")
	private String image;

	@Column(name="thumb_path")
	private String thumbPath;

	@Column(name = "displaySort")
	private Integer displaySort;

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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getThumbPath() {
		return thumbPath;
	}

	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}

	public vwEvent getEvent() {
		return event;
	}

	public void setEvent(vwEvent event) {
		this.event = event;
	}

	public Integer getDisplaySort() {
		return displaySort;
	}

	public void setDisplaySort(Integer displaySort) {
		this.displaySort = displaySort;
	}
}