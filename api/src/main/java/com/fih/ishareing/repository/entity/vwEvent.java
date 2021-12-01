package com.fih.ishareing.repository.entity;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the vw_event database table.
 * 
 */
@Entity
@Table(name = "vw_event")
public class vwEvent implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="owner_NPO_id")
	private Integer ownerNPOId;

	@Column(name="image_link_1")
	private String imageLink1;

	@Column(name="image_link_2")
	private String imageLink2;

	@Column(name="image_link_3")
	private String imageLink3;

	@Column(name="image_link_4")
	private String imageLink4;

	@Column(name="image_link_5")
	private String imageLink5;

	@Column(name="uid")
	private String uid;

	@Column(name="tags")
	private String tags;

	@Column(name="pub_date")
	private Timestamp pubDate;

	@Column(name="happen_date")
	private Timestamp happenDate;

	@Column(name="close_date")
	private Timestamp closeDate;

	@Column(name="register_deadline_date")
	private Timestamp registerDeadlineDate;

	@Column(name="subject")
	private String subject;

	@Column(name="description")
	@Type(type="text")
	private String description;

	@Column(name="event_hour")
	private Double eventHour;

	@Column(name="focus_num")
	private Integer focusNum;

	@Column(name="address_city")
	private String addressCity;

	@Column(name="address")
	private String address;

	@Column(name="insurance")
	private Boolean insurance;

	@Column(name="insurance_description")
	@Type(type="text")
	private String insuranceDescription;

	@Column(name="volunteer_training")
	private Integer volunteerTraining;

	@Column(name="volunteer_training_description")
	@Type(type="text")
	private String volunteerTrainingDescription;

	@Column(name="lat")
	private Double lat;

	@Column(name="lng")
	private Double lng;

	@Column(name="required_volunteer_number")
	private Integer requiredVolunteerNumber;

	@Column(name="current_volunteer_number")
	private Integer currentVolunteerNumber;

	@Column(name="required_group")
	private Integer requiredGroup;

	@Column(name="skills_description")
	private String skillsDescription;

	@Column(name="user_account_id")
	private Integer userAccountId;

	@Column(name="thumb_path")
	private String thumbPath;

	@Column(name="reply_num")
	private Integer replyNum;

	@Column(name="rating_user_num")
	private Integer ratingUserNum;

	@Column(name="total_rating_score")
	private Double totalRatingScore;

	@Column(name="is_volunteer_event")
	private Boolean volunteerEvent;

	@Column(name="is_urgent")
	private Boolean isUrgent;

	@Column(name="leave_uid")
	private String leaveUid;

	@Column(name="require_signout")
	private Boolean requireSignout;

	@Column(name="is_short")
	private Boolean isShort;

	@Column(name="volunteer_type")
	private String volunteerType;

	@Column(name="donation_serial")
	private String donationSerial;

	@Column(name="donation_start_date")
	private Timestamp donationStartDate;

	@Column(name="donation_end_date")
	private Timestamp donationEndDate;

	@Column(name="service_type")
	private String serviceType;

	@Column(name="foreign_third_party_id")
	private String foreignThirdPartyId;

	@Column(name="npoName")
	private String npoName;

	@Column(name="isFull")
	private Boolean isFull;

	@Column(name="is_enterprise")
	private Boolean enterprise;

	@Column(name="promote")
	private Boolean promote;

	@Column(name="note")
	private String note;

	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
	@BatchSize(size = 100)
	@OrderBy("id DESC")
	private List<tbCoreEventResultImage> tbCoreEventResultImageList;

	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
	@BatchSize(size = 100)
	@OrderBy("replyTime DESC")
	private List<vwEventReply> vwEventReplyList;

	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
	@BatchSize(size = 100)
	@OrderBy("id ASC")
	private List<tbCoreSkillGroup> tbCoreSkillGroupList;

	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
	@BatchSize(size = 100)
	private List<vwEventCooperationNpos> tbCoreEventCooperationNpoList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOwnerNPOId() {
		return ownerNPOId;
	}

	public void setOwnerNPOId(Integer ownerNPOId) {
		this.ownerNPOId = ownerNPOId;
	}

	public String getImageLink1() {
		return imageLink1;
	}

	public void setImageLink1(String imageLink1) {
		this.imageLink1 = imageLink1;
	}

	public String getImageLink2() {
		return imageLink2;
	}

	public void setImageLink2(String imageLink2) {
		this.imageLink2 = imageLink2;
	}

	public String getImageLink3() {
		return imageLink3;
	}

	public void setImageLink3(String imageLink3) {
		this.imageLink3 = imageLink3;
	}

	public String getImageLink4() {
		return imageLink4;
	}

	public void setImageLink4(String imageLink4) {
		this.imageLink4 = imageLink4;
	}

	public String getImageLink5() {
		return imageLink5;
	}

	public void setImageLink5(String imageLink5) {
		this.imageLink5 = imageLink5;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Timestamp getPubDate() {
		return pubDate;
	}

	public void setPubDate(Timestamp pubDate) {
		this.pubDate = pubDate;
	}

	public Timestamp getHappenDate() {
		return happenDate;
	}

	public void setHappenDate(Timestamp happenDate) {
		this.happenDate = happenDate;
	}

	public Timestamp getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Timestamp closeDate) {
		this.closeDate = closeDate;
	}

	public Timestamp getRegisterDeadlineDate() {
		return registerDeadlineDate;
	}

	public void setRegisterDeadlineDate(Timestamp registerDeadlineDate) {
		this.registerDeadlineDate = registerDeadlineDate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getEventHour() {
		return eventHour;
	}

	public void setEventHour(Double eventHour) {
		this.eventHour = eventHour;
	}

	public Integer getFocusNum() {
		return focusNum;
	}

	public void setFocusNum(Integer focusNum) {
		this.focusNum = focusNum;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getInsurance() {
		return insurance;
	}

	public void setInsurance(Boolean insurance) {
		this.insurance = insurance;
	}

	public String getInsuranceDescription() {
		return insuranceDescription;
	}

	public void setInsuranceDescription(String insuranceDescription) {
		this.insuranceDescription = insuranceDescription;
	}

	public Integer getVolunteerTraining() {
		return volunteerTraining;
	}

	public void setVolunteerTraining(Integer volunteerTraining) {
		this.volunteerTraining = volunteerTraining;
	}

	public String getVolunteerTrainingDescription() {
		return volunteerTrainingDescription;
	}

	public void setVolunteerTrainingDescription(String volunteerTrainingDescription) {
		this.volunteerTrainingDescription = volunteerTrainingDescription;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Integer getRequiredVolunteerNumber() {
		return requiredVolunteerNumber;
	}

	public void setRequiredVolunteerNumber(Integer requiredVolunteerNumber) {
		this.requiredVolunteerNumber = requiredVolunteerNumber;
	}

	public Integer getCurrentVolunteerNumber() {
		return currentVolunteerNumber;
	}

	public void setCurrentVolunteerNumber(Integer currentVolunteerNumber) {
		this.currentVolunteerNumber = currentVolunteerNumber;
	}

	public Integer getRequiredGroup() {
		return requiredGroup;
	}

	public void setRequiredGroup(Integer requiredGroup) {
		this.requiredGroup = requiredGroup;
	}

	public String getSkillsDescription() {
		return skillsDescription;
	}

	public void setSkillsDescription(String skillsDescription) {
		this.skillsDescription = skillsDescription;
	}

	public Integer getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(Integer userAccountId) {
		this.userAccountId = userAccountId;
	}

	public String getThumbPath() {
		return thumbPath;
	}

	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}

	public Integer getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(Integer replyNum) {
		this.replyNum = replyNum;
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

	public Boolean getUrgent() {
		return isUrgent;
	}

	public void setUrgent(Boolean urgent) {
		isUrgent = urgent;
	}

	public String getLeaveUid() {
		return leaveUid;
	}

	public void setLeaveUid(String leaveUid) {
		this.leaveUid = leaveUid;
	}

	public Boolean getRequireSignout() {
		return requireSignout;
	}

	public void setRequireSignout(Boolean requireSignout) {
		this.requireSignout = requireSignout;
	}

	public Boolean getShort() {
		return isShort;
	}

	public void setShort(Boolean aShort) {
		isShort = aShort;
	}

	public String getVolunteerType() {
		return volunteerType;
	}

	public void setVolunteerType(String volunteerType) {
		this.volunteerType = volunteerType;
	}

	public String getDonationSerial() {
		return donationSerial;
	}

	public void setDonationSerial(String donationSerial) {
		this.donationSerial = donationSerial;
	}

	public Timestamp getDonationStartDate() {
		return donationStartDate;
	}

	public void setDonationStartDate(Timestamp donationStartDate) {
		this.donationStartDate = donationStartDate;
	}

	public Timestamp getDonationEndDate() {
		return donationEndDate;
	}

	public void setDonationEndDate(Timestamp donationEndDate) {
		this.donationEndDate = donationEndDate;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getForeignThirdPartyId() {
		return foreignThirdPartyId;
	}

	public void setForeignThirdPartyId(String foreignThirdPartyId) {
		this.foreignThirdPartyId = foreignThirdPartyId;
	}

	public String getNpoName() {
		return npoName;
	}

	public void setNpoName(String npoName) {
		this.npoName = npoName;
	}

	public Boolean getFull() {
		return isFull;
	}

	public void setFull(Boolean full) {
		isFull = full;
	}

	public Boolean getVolunteerEvent() {
		return volunteerEvent;
	}

	public void setVolunteerEvent(Boolean volunteerEvent) {
		this.volunteerEvent = volunteerEvent;
	}

	public Boolean getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Boolean enterprise) {
		this.enterprise = enterprise;
	}

	public List<tbCoreEventResultImage> getTbCoreEventResultImageList() {
		return tbCoreEventResultImageList;
	}

	public void setTbCoreEventResultImageList(List<tbCoreEventResultImage> tbCoreEventResultImageList) {
		this.tbCoreEventResultImageList = tbCoreEventResultImageList;
	}

	public List<vwEventReply> getVwEventReplyList() {
		return vwEventReplyList;
	}

	public void setVwEventReplyList(List<vwEventReply> vwEventReplyList) {
		this.vwEventReplyList = vwEventReplyList;
	}

	public List<tbCoreSkillGroup> getTbCoreSkillGroupList() {
		return tbCoreSkillGroupList;
	}

	public void setTbCoreSkillGroupList(List<tbCoreSkillGroup> tbCoreSkillGroupList) {
		this.tbCoreSkillGroupList = tbCoreSkillGroupList;
	}

	public Boolean getPromote() {
		return promote;
	}

	public void setPromote(Boolean promote) {
		this.promote = promote;
	}

	public List<vwEventCooperationNpos> getTbCoreEventCooperationNpoList() {
		return tbCoreEventCooperationNpoList;
	}

	public void setTbCoreEventCooperationNpoList(List<vwEventCooperationNpos> tbCoreEventCooperationNpoList) {
		this.tbCoreEventCooperationNpoList = tbCoreEventCooperationNpoList;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}