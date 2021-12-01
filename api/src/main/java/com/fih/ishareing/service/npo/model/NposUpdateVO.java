package com.fih.ishareing.service.npo.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NposUpdateVO {

    @NotNull
    private Integer id;

    private Integer userId;

    @NotBlank
    @Size(max = 300)
    private String name;

    @Size(max = 1000)
    private String description;

    @Size(max = 50)
    private String registerNumber;

    @Size(max = 50)
    private String serialNumber;

    @NotNull
    @Size(max = 50)
    private String serviceTarget;

    @Size(max = 100)
    private String contactName;

    @Size(max = 30)
    private String contactPhone;

    @Size(max = 254)
    private String contactEmail;

    @Size(max = 100)
    private String contactJob;

    @Size(max = 100)
    private String contact2Name;

    @Size(max = 30)
    private String contact2Phone;

    @Size(max = 254)
    private String contact2Email;

    @Size(max = 100)
    private String contact2Job;

    @Size(max = 100)
    private String contactAddress;

    @Size(max = 200)
    private String contactWebsite;

    @Size(max = 200)
    private String contactSite;
    private Integer administratorId;

    @Size(max = 200)
    private String youtubeCode;

    private Boolean verified;
    private Boolean admViewed;
    private Boolean inventory;
    private Boolean enterprise;
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

    public String getServiceTarget() {
        return serviceTarget;
    }

    public void setServiceTarget(String serviceTarget) {
        this.serviceTarget = serviceTarget;
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

    public Integer getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(Integer administratorId) {
        this.administratorId = administratorId;
    }

    public String getYoutubeCode() {
        return youtubeCode;
    }

    public void setYoutubeCode(String youtubeCode) {
        this.youtubeCode = youtubeCode;
    }

    public Boolean getInventory() {
        return inventory;
    }

    public void setInventory(Boolean inventory) {
        this.inventory = inventory;
    }

    public Boolean getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Boolean enterprise) {
        this.enterprise = enterprise;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Boolean getAdmViewed() {
        return admViewed;
    }

    public void setAdmViewed(Boolean admViewed) {
        this.admViewed = admViewed;
    }

    public Boolean getPromote() {
        return promote;
    }

    public void setPromote(Boolean promote) {
        this.promote = promote;
    }
}
