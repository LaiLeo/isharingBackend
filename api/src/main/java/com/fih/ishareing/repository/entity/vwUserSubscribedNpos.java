package com.fih.ishareing.repository.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the vw_userSubscribedNpos database table.
 * 
 */
@Entity
@Table(name = "vw_userSubscribedNpos")
public class vwUserSubscribedNpos implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;

	@Column(name="npoId")
	private Integer npoId;

	@Column(name="userId")
	private Integer userId;

	@Column(name="npoName")
	private String npoName;

	@Column(name="npoUid")
	private String npoUid;

	@Column(name="npoIcon")
	private String npoIcon;

	@Column(name="isVerified")
	private Boolean isVerified;

	@Column(name="admViewed")
	private Boolean admViewed;

	@Column(name="admId")
	private Integer admId;

	@Column(name="ratingUserNum")
	private Integer ratingUserNum;

	@Column(name="totalRatingScore")
	private Double totalRatingScore;

	@Column(name="subscribedUserNum")
	private Integer subscribedUserNum;

	@Column(name="joinedUserNum")
	private Integer joinedUserNum;

	@Column(name="eventNum")
	private Integer eventNum;

	@Column(name="focusedDate")
	private Timestamp focusedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNpoId() {
		return npoId;
	}

	public void setNpoId(Integer npoId) {
		this.npoId = npoId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getNpoName() {
		return npoName;
	}

	public void setNpoName(String npoName) {
		this.npoName = npoName;
	}

	public String getNpoUid() {
		return npoUid;
	}

	public void setNpoUid(String npoUid) {
		this.npoUid = npoUid;
	}

	public String getNpoIcon() {
		return npoIcon;
	}

	public void setNpoIcon(String npoIcon) {
		this.npoIcon = npoIcon;
	}

	public Boolean getVerified() {
		return isVerified;
	}

	public void setVerified(Boolean verified) {
		isVerified = verified;
	}

	public Boolean getAdmViewed() {
		return admViewed;
	}

	public void setAdmViewed(Boolean admViewed) {
		this.admViewed = admViewed;
	}

	public Integer getAdmId() {
		return admId;
	}

	public void setAdmId(Integer admId) {
		this.admId = admId;
	}

	public Integer getRatingUserNum() {
		return ratingUserNum;
	}

	public void setRatingUserNum(Integer ratingUserNum) {
		this.ratingUserNum = ratingUserNum;
	}

	public Double getTotalRatingScore() {
		return totalRatingScore;
	}

	public void setTotalRatingScore(Double totalRatingScore) {
		this.totalRatingScore = totalRatingScore;
	}

	public Integer getSubscribedUserNum() {
		return subscribedUserNum;
	}

	public void setSubscribedUserNum(Integer subscribedUserNum) {
		this.subscribedUserNum = subscribedUserNum;
	}

	public Integer getJoinedUserNum() {
		return joinedUserNum;
	}

	public void setJoinedUserNum(Integer joinedUserNum) {
		this.joinedUserNum = joinedUserNum;
	}

	public Integer getEventNum() {
		return eventNum;
	}

	public void setEventNum(Integer eventNum) {
		this.eventNum = eventNum;
	}

	public Timestamp getFocusedDate() {
		return focusedDate;
	}

	public void setFocusedDate(Timestamp focusedDate) {
		this.focusedDate = focusedDate;
	}
}