package com.fih.ishareing.repository.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the core_npo database table.
 * 
 */
@Entity
@Table(name = "core_npo")
public class tbCoreNpo implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="user_id")
	private Integer userId;

	@Column(name="name")
	private String name;

	@Column(name="uid")
	private String uid;

	@Column(name="npo_icon")
	private String npoIcon;

	@Column(name="description")
	@Type(type="text")
	private String description;

	@Column(name="register_number")
	private String registerNumber;

	@Column(name="serial_number")
	private String serialNumber;

	@Column(name="verified_image")
	private String verifiedImage;

	@Column(name="service_target")
	private String serviceTarget;

	@Column(name="government_register_image")
	private String governmentRegisterImage;

	@Column(name="pub_date")
	private Timestamp pubDate;

	@Column(name="contact_name")
	private String contactName;

	@Column(name="contact_phone")
	private String contactPhone;

	@Column(name="contact_email")
	private String contactEmail;

	@Column(name="contact_job")
	private String contactJob;

	@Column(name="contact2_name")
	private String contact2Name;

	@Column(name="contact2_phone")
	private String contact2Phone;

	@Column(name="contact2_email")
	private String contact2Email;

	@Column(name="contact2_job")
	private String contact2Job;

	@Column(name="contact_address")
	private String contactAddress;

	@Column(name="contact_website")
	private String contactWebsite;

	@Column(name="contact_site")
	private String contactSite;

	@Column(name="is_verified")
	private Boolean isVerified;

	@Column(name="adm_viewed")
	private Boolean admViewed;

	@Column(name="administrator_id")
	private Integer administratorId;

	@Column(name="thumb_path")
	private String thumbPath;

	@Column(name="rating_user_num")
	private Integer ratingUserNum;

	@Column(name="total_rating_score")
	private Double totalRatingScore;

	@Column(name="subscribed_user_num")
	private Integer subscribedUserNum;

	@Column(name="joined_user_num")
	private Integer joinedUserNum;

	@Column(name="event_num")
	private Integer eventNum;

	@Column(name="youtube_code")
	private String youtubeCode;

	@Column(name="is_inventory")
	private Boolean isInventory;

	@Column(name="is_enterprise")
	private Boolean isEnterprise;

	@Column(name="promote")
	private Boolean promote;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNpoIcon() {
		return npoIcon;
	}

	public void setNpoIcon(String npoIcon) {
		this.npoIcon = npoIcon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRegisterNumber() {
		return registerNumber;
	}

	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getVerifiedImage() {
		return verifiedImage;
	}

	public void setVerifiedImage(String verifiedImage) {
		this.verifiedImage = verifiedImage;
	}

	public String getServiceTarget() {
		return serviceTarget;
	}

	public void setServiceTarget(String serviceTarget) {
		this.serviceTarget = serviceTarget;
	}

	public String getGovernmentRegisterImage() {
		return governmentRegisterImage;
	}

	public void setGovernmentRegisterImage(String governmentRegisterImage) {
		this.governmentRegisterImage = governmentRegisterImage;
	}

	public Timestamp getPubDate() {
		return pubDate;
	}

	public void setPubDate(Timestamp pubDate) {
		this.pubDate = pubDate;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactJob() {
		return contactJob;
	}

	public void setContactJob(String contactJob) {
		this.contactJob = contactJob;
	}

	public String getContact2Name() {
		return contact2Name;
	}

	public void setContact2Name(String contact2Name) {
		this.contact2Name = contact2Name;
	}

	public String getContact2Phone() {
		return contact2Phone;
	}

	public void setContact2Phone(String contact2Phone) {
		this.contact2Phone = contact2Phone;
	}

	public String getContact2Email() {
		return contact2Email;
	}

	public void setContact2Email(String contact2Email) {
		this.contact2Email = contact2Email;
	}

	public String getContact2Job() {
		return contact2Job;
	}

	public void setContact2Job(String contact2Job) {
		this.contact2Job = contact2Job;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getContactWebsite() {
		return contactWebsite;
	}

	public void setContactWebsite(String contactWebsite) {
		this.contactWebsite = contactWebsite;
	}

	public String getContactSite() {
		return contactSite;
	}

	public void setContactSite(String contactSite) {
		this.contactSite = contactSite;
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

	public Integer getAdministratorId() {
		return administratorId;
	}

	public void setAdministratorId(Integer administratorId) {
		this.administratorId = administratorId;
	}

	public String getThumbPath() {
		return thumbPath;
	}

	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
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

	public String getYoutubeCode() {
		return youtubeCode;
	}

	public void setYoutubeCode(String youtubeCode) {
		this.youtubeCode = youtubeCode;
	}

	public Boolean getInventory() {
		return isInventory;
	}

	public void setInventory(Boolean inventory) {
		isInventory = inventory;
	}

	public Boolean getEnterprise() {
		return isEnterprise;
	}

	public void setEnterprise(Boolean enterprise) {
		isEnterprise = enterprise;
	}

	public Boolean getPromote() {
		return promote;
	}

	public void setPromote(Boolean promote) {
		this.promote = promote;
	}
}