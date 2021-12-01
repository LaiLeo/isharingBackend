package com.fih.ishareing.service.npo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fih.ishareing.repository.criteria.annotations.QueryCondition;
import com.fih.ishareing.repository.criteria.annotations.QueryConditions;
import com.fih.ishareing.service.event.model.EventReplyVO;
import com.fih.ishareing.service.event.model.EventResultImageVO;
import com.fih.ishareing.service.event.model.EventSkillGroupVO;

import java.sql.Timestamp;
import java.util.List;

public class NpoVO {
    private Integer id;
    private Integer userId;
    private String name;
    private String uid;
    private String npoIcon;
    private String description;
    private String registerNumber;
    private String serialNumber;
    private String verifiedImage;
    private String serviceTarget;
    private String registerImage;
    private Timestamp pubDate;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private String contactJob;
    private String contact2Name;
    private String contact2Phone;
    private String contact2Email;
    private String contact2Job;
    private String contactAddress;
    private String contactWebsite;
    private String contactSite;
    @JsonProperty("isVerified")
    private Boolean verified;
    private Boolean admViewed;
    private Integer administratorId;
    private String thumbPath;
    private Integer ratingUserNum;
    private Integer totalRatingScore;
    private Integer subscribedUserNum;
    private Integer joinedUserNum;
    private Integer eventNum;
    private String youtubeCode;
    @JsonProperty("isInventory")
    private Boolean inventory;
    @JsonProperty("isEnterprise")
    private Boolean enterprise;
    private Boolean promote;

    @QueryCondition(searchable = true, sortable = true, field = "id", databaseField = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @QueryCondition(searchable = true, sortable = true, field = "userId", databaseField = "userId")
    public Integer getUserId() {
        return userId;
    }
    @QueryCondition(searchable = true, sortable = true, field = "name", databaseField = "name")
    public String getName() {
        return name;
    }
    @QueryCondition(searchable = true, sortable = false, field = "uid", databaseField = "uid")
    public String getUid() {
        return uid;
    }

    public String getNpoIcon() {
        return npoIcon;
    }

    public String getDescription() {
        return description;
    }
    @QueryCondition(searchable = true, sortable = true, field = "registerNumber", databaseField = "registerNumber")
    public String getRegisterNumber() {
        return registerNumber;
    }
    @QueryCondition(searchable = true, sortable = true, field = "serialNumber", databaseField = "serialNumber")
    public String getSerialNumber() {
        return serialNumber;
    }

    public String getRegisterImage() {
        return registerImage;
    }
    @QueryCondition(searchable = true, sortable = true, field = "pubDate", databaseField = "pubDate")
    public Timestamp getPubDate() {
        return pubDate;
    }
    @QueryCondition(searchable = true, sortable = false, field = "contactName", databaseField = "contactName")
    public String getContactName() {
        return contactName;
    }
    @QueryCondition(searchable = true, sortable = false, field = "contactPhone", databaseField = "contactPhone")
    public String getContactPhone() {
        return contactPhone;
    }
    @QueryCondition(searchable = true, sortable = false, field = "contactEmail", databaseField = "contactEmail")
    public String getContactEmail() {
        return contactEmail;
    }
    @QueryCondition(searchable = true, sortable = false, field = "contactJob", databaseField = "contactJob")
    public String getContactJob() {
        return contactJob;
    }
    @QueryCondition(searchable = true, sortable = false, field = "contact2Name", databaseField = "contact2Name")
    public String getContact2Name() {
        return contact2Name;
    }
    @QueryCondition(searchable = true, sortable = false, field = "contact2Phone", databaseField = "contact2Phone")
    public String getContact2Phone() {
        return contact2Phone;
    }
    @QueryCondition(searchable = true, sortable = false, field = "contact2Email", databaseField = "contact2Email")
    public String getContact2Email() {
        return contact2Email;
    }
    @QueryCondition(searchable = true, sortable = false, field = "contact2Job", databaseField = "contact2Job")
    public String getContact2Job() {
        return contact2Job;
    }
    @QueryCondition(searchable = true, sortable = false, field = "contactAddress", databaseField = "contactAddress")
    public String getContactAddress() {
        return contactAddress;
    }

    public String getContactWebsite() {
        return contactWebsite;
    }

    public String getContactSite() {
        return contactSite;
    }
    @QueryCondition(searchable = true, sortable = true, field = "isVerified", databaseField = "isVerified")
    public Boolean getVerified() {
        return this.verified;
    }
    @QueryCondition(searchable = true, sortable = true, field = "admViewed", databaseField = "admViewed")
    public Boolean getAdmViewed() {
        return admViewed;
    }
    @QueryCondition(searchable = true, sortable = false, field = "administratorId", databaseField = "administratorId")
    public Integer getAdministratorId() {
        return administratorId;
    }

    public String getThumbPath() {
        return thumbPath;
    }
    @QueryCondition(searchable = true, sortable = true, field = "ratingUserNum", databaseField = "ratingUserNum")
    public Integer getRatingUserNum() {
        return ratingUserNum;
    }
    @QueryCondition(searchable = true, sortable = true, field = "totalRatingScore", databaseField = "totalRatingScore")
    public Integer getTotalRatingScore() {
        return totalRatingScore;
    }
    @QueryCondition(searchable = true, sortable = true, field = "subscribedUserNum", databaseField = "subscribedUserNum")
    public Integer getSubscribedUserNum() {
        return subscribedUserNum;
    }
    @QueryCondition(searchable = true, sortable = true, field = "joinedUserNum", databaseField = "joinedUserNum")
    public Integer getJoinedUserNum() {
        return joinedUserNum;
    }
    @QueryCondition(searchable = true, sortable = true, field = "eventNum", databaseField = "eventNum")
    public Integer getEventNum() {
        return eventNum;
    }

    public String getYoutubeCode() {
        return youtubeCode;
    }
    @QueryCondition(searchable = true, sortable = false, field = "isInventory", databaseField = "isInventory")
    public Boolean getInventory() {
        return this.inventory;
    }
    @QueryCondition(searchable = true, sortable = false, field = "isEnterprise", databaseField = "isEnterprise")
    public Boolean getEnterprise() {
        return this.enterprise;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setNpoIcon(String npoIcon) {
        this.npoIcon = npoIcon;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setRegisterImage(String registerImage) {
        this.registerImage = registerImage;
    }

    public void setPubDate(Timestamp pubDate) {
        this.pubDate = pubDate;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setContactJob(String contactJob) {
        this.contactJob = contactJob;
    }

    public void setContact2Name(String contact2Name) {
        this.contact2Name = contact2Name;
    }

    public void setContact2Phone(String contact2Phone) {
        this.contact2Phone = contact2Phone;
    }

    public void setContact2Email(String contact2Email) {
        this.contact2Email = contact2Email;
    }

    public void setContact2Job(String contact2Job) {
        this.contact2Job = contact2Job;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public void setContactWebsite(String contactWebsite) {
        this.contactWebsite = contactWebsite;
    }

    public void setContactSite(String contactSite) {
        this.contactSite = contactSite;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public void setAdmViewed(Boolean admViewed) {
        this.admViewed = admViewed;
    }

    public void setAdministratorId(Integer administratorId) {
        this.administratorId = administratorId;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public void setRatingUserNum(Integer ratingUserNum) {
        this.ratingUserNum = ratingUserNum;
    }

    public void setTotalRatingScore(Integer totalRatingScore) {
        this.totalRatingScore = totalRatingScore;
    }

    public void setSubscribedUserNum(Integer subscribedUserNum) {
        this.subscribedUserNum = subscribedUserNum;
    }

    public void setJoinedUserNum(Integer joinedUserNum) {
        this.joinedUserNum = joinedUserNum;
    }

    public void setEventNum(Integer eventNum) {
        this.eventNum = eventNum;
    }

    public void setYoutubeCode(String youtubeCode) {
        this.youtubeCode = youtubeCode;
    }

    public void setInventory(Boolean inventory) {
        this.inventory = inventory;
    }

    public void setEnterprise(Boolean enterprise) {
        this.enterprise = enterprise;
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

    @QueryCondition(searchable = true, sortable = true, field = "promote", databaseField = "promote")
    public Boolean getPromote() {
        return promote;
    }

    public void setPromote(Boolean promote) {
        this.promote = promote;
    }
}
