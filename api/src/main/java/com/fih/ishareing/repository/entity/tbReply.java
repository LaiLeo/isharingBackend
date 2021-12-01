package com.fih.ishareing.repository.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the core_reply database table.
 * 
 */
@Entity
@Table(name = "core_reply")
public class tbReply implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="user_account_id")
	private Integer userAccountId;

	@Column(name="reply_time")
	private Timestamp replyTime;

	@Column(name="message")
	private String message;

	@Column(name="image")
	private String image;

	@Column(name="thumb_path")
	private String thumbPath;

	@Column(name="event_id")
	private Integer eventId;

	@ManyToOne
	@JoinColumn(name="event_id", insertable=false, updatable=false)
	private vwEvent event;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(Integer userAccountId) {
		this.userAccountId = userAccountId;
	}

	public Timestamp getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Timestamp replyTime) {
		this.replyTime = replyTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public vwEvent getEvent() {
		return event;
	}

	public void setEvent(vwEvent event) {
		this.event = event;
	}
}