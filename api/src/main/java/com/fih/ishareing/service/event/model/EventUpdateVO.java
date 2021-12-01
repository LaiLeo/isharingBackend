package com.fih.ishareing.service.event.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class EventUpdateVO {
    @NotNull
    private Integer id;
    private Integer npoId;
    private String tags;
    private String happenDate;
    private String closeDate;
    private String registerDeadlineDate;
    private String subject;
    private String description;
    private Double eventHour;
    private String addressCity;
    private String address;
    private Boolean insurance;
    private String insuranceDescription;
    private Boolean volunteerTraining;
    private String volunteerTrainingDesc;
    private Double lat;
    private Double lng;
    private Integer requiredVolunteerNum;
    private Integer currentVolunteerNum;
    private Boolean requiredGroup;
    private String skillsDescription;
    @NotNull
    @JsonProperty(value = "isSupplyEvent")
    private Boolean isSupplyEvent;
    @JsonProperty(value = "isUrgent")
    private Boolean isUrgent;
    private Boolean requireSignout;
    private Boolean shortTerm;
    private String volunteerType;
    private String donationSerial;
    private String donationStartDate;
    private String donationEndDate;
    private String serviceType;
    private String foreignThirdPartyId;
    private Boolean promote;
    private String[] cooperationNpoIds;
    private String note;

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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getHappenDate() {
        return happenDate;
    }

    public void setHappenDate(String happenDate) {
        this.happenDate = happenDate;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public String getRegisterDeadlineDate() {
        return registerDeadlineDate;
    }

    public void setRegisterDeadlineDate(String registerDeadlineDate) {
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

    public Boolean getVolunteerTraining() {
        return volunteerTraining;
    }

    public void setVolunteerTraining(Boolean volunteerTraining) {
        this.volunteerTraining = volunteerTraining;
    }

    public String getVolunteerTrainingDesc() {
        return volunteerTrainingDesc;
    }

    public void setVolunteerTrainingDesc(String volunteerTrainingDesc) {
        this.volunteerTrainingDesc = volunteerTrainingDesc;
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

    public Integer getRequiredVolunteerNum() {
        return requiredVolunteerNum;
    }

    public void setRequiredVolunteerNum(Integer requiredVolunteerNum) {
        this.requiredVolunteerNum = requiredVolunteerNum;
    }

    public Boolean getRequiredGroup() {
        return requiredGroup;
    }

    public void setRequiredGroup(Boolean requiredGroup) {
        this.requiredGroup = requiredGroup;
    }

    public String getSkillsDescription() {
        return skillsDescription;
    }

    public void setSkillsDescription(String skillsDescription) {
        this.skillsDescription = skillsDescription;
    }

    public Boolean getSupplyEvent() {
        return isSupplyEvent;
    }

    public void setSupplyEvent(Boolean supplyEvent) {
        isSupplyEvent = supplyEvent;
    }

    public Boolean getUrgent() {
        return isUrgent;
    }

    public void setUrgent(Boolean urgent) {
        isUrgent = urgent;
    }

    public Boolean getRequireSignout() {
        return requireSignout;
    }

    public void setRequireSignout(Boolean requireSignout) {
        this.requireSignout = requireSignout;
    }

    public Boolean getShortTerm() {
        return shortTerm;
    }

    public void setShortTerm(Boolean shortTerm) {
        this.shortTerm = shortTerm;
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

    public String getDonationStartDate() {
        return donationStartDate;
    }

    public void setDonationStartDate(String donationStartDate) {
        this.donationStartDate = donationStartDate;
    }

    public String getDonationEndDate() {
        return donationEndDate;
    }

    public void setDonationEndDate(String donationEndDate) {
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

    public Boolean getPromote() {
        return promote;
    }

    public void setPromote(Boolean promote) {
        this.promote = promote;
    }

    public String[] getCooperationNpoIds() {
        return cooperationNpoIds;
    }

    public void setCooperationNpoIds(String[] cooperationNpoIds) {
        this.cooperationNpoIds = cooperationNpoIds;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getCurrentVolunteerNum() {
        return currentVolunteerNum;
    }

    public void setCurrentVolunteerNum(Integer currentVolunteerNum) {
        this.currentVolunteerNum = currentVolunteerNum;
    }
}
