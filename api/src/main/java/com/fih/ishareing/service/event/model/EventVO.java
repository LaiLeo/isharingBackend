package com.fih.ishareing.service.event.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fih.ishareing.repository.criteria.annotations.QueryCondition;
import com.fih.ishareing.repository.criteria.annotations.QueryConditions;

import java.sql.Timestamp;
import java.util.List;

public class EventVO {
    private Integer id;
    private Integer npoId;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String uid;
    private String tags;
    private Timestamp pubDate;
    private Timestamp happenDate;
    private Timestamp closeDate;
    private Timestamp registerDeadlineDate;
    private String subject;
    private String description;
    private Double eventHour;
    private Integer focusNum;
    private String addressCity;
    private String address;
    private Boolean insurance;
    private String insuranceDescription;
    private Integer volunteerTraining;
    private String volunteerTrainingDesc;
    private Double lat;
    private Double lng;
    private Integer requiredVolunteerNum;
    private Integer currentVolunteerNum;
    private Integer requiredGroup;
    private String skillsDescription;
    private Integer userId;
    private String thumbPath;
    @JsonProperty("isVolunteerEvent")
    private Boolean volunteerEvent;
    @JsonProperty("isUrgent")
    private Boolean urgent;
    private Boolean requireSignout;
    @JsonProperty("isShort")
    private Boolean isShort;
    private String volunteerType;
    private String donationSerial;
    private Timestamp donationStartDate;
    private Timestamp donationEndDate;
    private String serviceType;
    private String foreignThirdPartyId;
    private String npoName;
    @JsonProperty("isFull")
    private Boolean full;
    @JsonProperty("isEnterprise")
    private Boolean enterprise;
    private Boolean promote;
    private String note;

    private List<EventSkillGroupVO> skillGroups;
    private List<EventResultImageVO> resultImages;
    private List<EventReplyVO> replys;
    private List<EventCooperationNposVO> cooperationNpos;

    @QueryCondition(searchable = true, sortable = true, field = "id", databaseField = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @QueryCondition(searchable = true, sortable = true, field = "npoId", databaseField = "ownerNPOId")
    public Integer getNpoId() {
        return npoId;
    }

    public String getImage1() {
        return image1;
    }

    public String getImage2() {
        return image2;
    }

    public String getImage3() {
        return image3;
    }

    public String getImage4() {
        return image4;
    }

    public String getImage5() {
        return image5;
    }

    @QueryCondition(searchable = true, sortable = false, field = "uid", databaseField = "uid")
    public String getUid() {
        return uid;
    }

    @QueryCondition(searchable = true, sortable = true, field = "tags", databaseField = "tags")
    public String getTags() {
        return tags;
    }

    @QueryCondition(searchable = true, sortable = true, field = "pubDate", databaseField = "pubDate")
    public Timestamp getPubDate() {
        return pubDate;
    }

    @QueryCondition(searchable = true, sortable = true, field = "happenDate", databaseField = "happenDate")
    public Timestamp getHappenDate() {
        return happenDate;
    }

    @QueryCondition(searchable = true, sortable = true, field = "closeDate", databaseField = "closeDate")
    public Timestamp getCloseDate() {
        return closeDate;
    }

    @QueryCondition(searchable = true, sortable = true, field = "registerDeadlineDate", databaseField = "registerDeadlineDate")
    public Timestamp getRegisterDeadlineDate() {
        return registerDeadlineDate;
    }

    @QueryCondition(searchable = true, sortable = true, field = "subject", databaseField = "subject")
    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    @QueryCondition(searchable = true, sortable = true, field = "eventHour", databaseField = "eventHour")
    public Double getEventHour() {
        return eventHour;
    }

    @QueryCondition(searchable = true, sortable = true, field = "focusNum", databaseField = "focusNum")
    public Integer getFocusNum() {
        return focusNum;
    }

    @QueryCondition(searchable = true, sortable = true, field = "addressCity", databaseField = "addressCity")
    public String getAddressCity() {
        return addressCity;
    }

    @QueryCondition(searchable = true, sortable = true, field = "address", databaseField = "address")
    public String getAddress() {
        return address;
    }

    @QueryCondition(searchable = true, sortable = true, field = "insurance", databaseField = "insurance")
    public Boolean getInsurance() {
        return insurance;
    }

    public String getInsuranceDescription() {
        return insuranceDescription;
    }

    @QueryCondition(searchable = true, sortable = true, field = "volunteerTraining", databaseField = "volunteerTraining")
    public Integer getVolunteerTraining() {
        return volunteerTraining;
    }

    public String getVolunteerTrainingDesc() {
        return volunteerTrainingDesc;
    }

    @QueryCondition(searchable = true, sortable = true, field = "lat", databaseField = "lat")
    public Double getLat() {
        return lat;
    }

    @QueryCondition(searchable = true, sortable = true, field = "lng", databaseField = "lng")
    public Double getLng() {
        return lng;
    }

    @QueryCondition(searchable = true, sortable = true, field = "requiredVolunteerNum", databaseField = "requiredVolunteerNum")
    public Integer getRequiredVolunteerNum() {
        return requiredVolunteerNum;
    }

    @QueryCondition(searchable = true, sortable = true, field = "currentVolunteerNum", databaseField = "currentVolunteerNum")
    public Integer getCurrentVolunteerNum() {
        return currentVolunteerNum;
    }

    @QueryCondition(searchable = true, sortable = true, field = "requiredGroup", databaseField = "requiredGroup")
    public Integer getRequiredGroup() {
        return requiredGroup;
    }

    public String getSkillsDescription() {
        return skillsDescription;
    }

    @QueryCondition(searchable = true, sortable = true, field = "userId", databaseField = "userId")
    public Integer getUserId() {
        return userId;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    @QueryCondition(searchable = true, sortable = true, field = "isVolunteerEvent", databaseField = "volunteerEvent")
    public Boolean getVolunteerEvent() {
        return this.volunteerEvent;
    }

    @QueryCondition(searchable = true, sortable = true, field = "isUrgent", databaseField = "isUrgent")
    public Boolean getUrgent() {
        return this.urgent;
    }

    @QueryCondition(searchable = true, sortable = true, field = "requireSignout", databaseField = "requireSignout")
    public Boolean getRequireSignout() {
        return requireSignout;
    }

    @QueryCondition(searchable = true, sortable = true, field = "isShort", databaseField = "isShort")
    public Boolean getShort() {
        return isShort;
    }

    @QueryCondition(searchable = true, sortable = true, field = "volunteerType", databaseField = "volunteerType")
    public String getVolunteerType() {
        return volunteerType;
    }

    @QueryCondition(searchable = true, sortable = true, field = "donationSerial", databaseField = "donationSerial")
    public String getDonationSerial() {
        return donationSerial;
    }

    @QueryCondition(searchable = true, sortable = true, field = "donationStartDate", databaseField = "donationStartDate")
    public Timestamp getDonationStartDate() {
        return donationStartDate;
    }

    @QueryCondition(searchable = true, sortable = true, field = "donationEndDate", databaseField = "donationEndDate")
    public Timestamp getDonationEndDate() {
        return donationEndDate;
    }

    @QueryCondition(searchable = true, sortable = true, field = "serviceType", databaseField = "serviceType")
    public String getServiceType() {
        return serviceType;
    }

    @QueryCondition(searchable = true, sortable = false, field = "foreignThirdPartyId", databaseField = "foreignThirdPartyId")
    public String getForeignThirdPartyId() {
        return foreignThirdPartyId;
    }

    @QueryCondition(searchable = true, sortable = true, field = "npoName", databaseField = "npoName")
    public String getNpoName() {
        return npoName;
    }

    @QueryCondition(searchable = true, sortable = true, field = "isFull", databaseField = "isFull")
    public Boolean getFull() {
        return this.full;
    }

    @QueryCondition(searchable = true, sortable = true, field = "isEnterprise", databaseField = "enterprise")
    public Boolean getEnterprise() {
        return this.enterprise;
    }

    public void setNpoId(Integer npoId) {
        this.npoId = npoId;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public void setImage5(String image5) {
        this.image5 = image5;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setPubDate(Timestamp pubDate) {
        this.pubDate = pubDate;
    }

    public void setHappenDate(Timestamp happenDate) {
        this.happenDate = happenDate;
    }

    public void setCloseDate(Timestamp closeDate) {
        this.closeDate = closeDate;
    }

    public void setRegisterDeadlineDate(Timestamp registerDeadlineDate) {
        this.registerDeadlineDate = registerDeadlineDate;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEventHour(Double eventHour) {
        this.eventHour = eventHour;
    }

    public void setFocusNum(Integer focusNum) {
        this.focusNum = focusNum;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setInsurance(Boolean insurance) {
        this.insurance = insurance;
    }

    public void setInsuranceDescription(String insuranceDescription) {
        this.insuranceDescription = insuranceDescription;
    }

    public void setVolunteerTraining(Integer volunteerTraining) {
        this.volunteerTraining = volunteerTraining;
    }

    public void setVolunteerTrainingDesc(String volunteerTrainingDesc) {
        this.volunteerTrainingDesc = volunteerTrainingDesc;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public void setRequiredVolunteerNum(Integer requiredVolunteerNum) {
        this.requiredVolunteerNum = requiredVolunteerNum;
    }

    public void setCurrentVolunteerNum(Integer currentVolunteerNum) {
        this.currentVolunteerNum = currentVolunteerNum;
    }

    public void setRequiredGroup(Integer requiredGroup) {
        this.requiredGroup = requiredGroup;
    }

    public void setSkillsDescription(String skillsDescription) {
        this.skillsDescription = skillsDescription;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public void setVolunteerEvent(Boolean volunteerEvent) {
        this.volunteerEvent = volunteerEvent;
    }

    public void setUrgent(Boolean urgent) {
        this.urgent = urgent;
    }

    public void setRequireSignout(Boolean requireSignout) {
        this.requireSignout = requireSignout;
    }

    public void setShort(Boolean aShort) {
        isShort = aShort;
    }

    public void setVolunteerType(String volunteerType) {
        this.volunteerType = volunteerType;
    }

    public void setDonationSerial(String donationSerial) {
        this.donationSerial = donationSerial;
    }

    public void setDonationStartDate(Timestamp donationStartDate) {
        this.donationStartDate = donationStartDate;
    }

    public void setDonationEndDate(Timestamp donationEndDate) {
        this.donationEndDate = donationEndDate;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public void setForeignThirdPartyId(String foreignThirdPartyId) {
        this.foreignThirdPartyId = foreignThirdPartyId;
    }

    public void setNpoName(String npoName) {
        this.npoName = npoName;
    }

    public void setFull(Boolean full) {
        this.full = full;
    }

    public void setEnterprise(Boolean enterprise) {
        this.enterprise = enterprise;
    }

    @QueryConditions(value = {
            @QueryCondition(searchable = true, sortable = false, field = "skillGroups.id", databaseField = "tbSkillGroupList.id"),
            @QueryCondition(searchable = true, sortable = false, field = "skillGroups.name", databaseField = "tbSkillGroupList.name"),
            @QueryCondition(searchable = true, sortable = false, field = "skillGroups.volunteerNumber", databaseField = "tbSkillGroupList.volunteerNumber"),
            @QueryCondition(searchable = true, sortable = false, field = "skillGroups.currentVolunteerNumber", databaseField = "tbSkillGroupList.currentVolunteerNumber")})
    public List<EventSkillGroupVO> getSkillGroups() {
        return skillGroups;
    }

    public void setSkillGroups(List<EventSkillGroupVO> skillGroups) {
        this.skillGroups = skillGroups;
    }

    public List<EventResultImageVO> getResultImages() {
        return resultImages;
    }

    public void setResultImages(List<EventResultImageVO> resultImages) {
        this.resultImages = resultImages;
    }

    @QueryConditions(value = {
            @QueryCondition(searchable = true, sortable = false, field = "replys.id", databaseField = "vwEventReplyList.id"),
            @QueryCondition(searchable = true, sortable = false, field = "replys.userId", databaseField = "vwEventReplyList.userId"),
            @QueryCondition(searchable = true, sortable = false, field = "replys.replyTime", databaseField = "vwEventReplyList.replyTime")})
    public List<EventReplyVO> getReplys() {
        return replys;
    }

    public void setReplys(List<EventReplyVO> replys) {
        this.replys = replys;
    }

    @QueryCondition(searchable = true, sortable = true, field = "promote", databaseField = "promote")
    public Boolean getPromote() {
        return promote;
    }

    public void setPromote(Boolean promote) {
        this.promote = promote;
    }

    @QueryConditions(value = {
            @QueryCondition(searchable = true, sortable = false, field = "cooperationNpos.id", databaseField = "tbCoreEventCooperationNpoList.npoId"),
            @QueryCondition(searchable = true, sortable = false, field = "cooperationNpos.name", databaseField = "tbCoreEventCooperationNpoList.npoName")})
    public List<EventCooperationNposVO> getCooperationNpos() {
        return cooperationNpos;
    }

    public void setCooperationNpos(List<EventCooperationNposVO> cooperationNpos) {
        this.cooperationNpos = cooperationNpos;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
