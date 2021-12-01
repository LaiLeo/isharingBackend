package com.fih.ishareing.repository.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The persistent class for the vw_event_reply database table.
 * 
 */
@Entity
@Table(name = "vw_event_reply")
public class vwEventReply implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

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

	@Column(name="userId")
	private Integer userId;

	@Column(name="userName")
	private String userName;

	@Column(name="userPhoto")
	private String userPhoto;

	@Column(name="userIcon")
	private String userIcon;

	@ManyToOne
	@JoinColumn(name="event_id", insertable=false, updatable=false)
	private vwEvent event;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public vwEvent getEvent() {
		return event;
	}

	public void setEvent(vwEvent event) {
		this.event = event;
	}
}