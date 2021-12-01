package com.fih.ishareing.service.event.model;

import javax.validation.constraints.NotNull;

public class EventRegisterVO {
    @NotNull
    private Integer id;

    @NotNull
    private String name;
    @NotNull
    private String phone;
    @NotNull
    private String email;

    private String skills;

    private String birthday;

    private String guardianName;

    private String guardianPhone;

    private Integer eventSkillGroupId;

    private String securityId;

    private String note;

    private String employeeSerialNumber;

    private String enterpriseSerialNumber;

    private String eventSkillGroupList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getGuardianPhone() {
        return guardianPhone;
    }

    public void setGuardianPhone(String guardianPhone) {
        this.guardianPhone = guardianPhone;
    }

    public Integer getEventSkillGroupId() {
        return eventSkillGroupId;
    }

    public void setEventSkillGroupId(Integer eventSkillGroupId) {
        this.eventSkillGroupId = eventSkillGroupId;
    }

    public String getSecurityId() {
        return securityId;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEmployeeSerialNumber() {
        return employeeSerialNumber;
    }

    public void setEmployeeSerialNumber(String employeeSerialNumber) {
        this.employeeSerialNumber = employeeSerialNumber;
    }

    public String getEnterpriseSerialNumber() {
        return enterpriseSerialNumber;
    }

    public void setEnterpriseSerialNumber(String enterpriseSerialNumber) {
        this.enterpriseSerialNumber = enterpriseSerialNumber;
    }

    public String getEventSkillGroupList() {
        return eventSkillGroupList;
    }

    public void setEventSkillGroupList(String eventSkillGroupList) {
        this.eventSkillGroupList = eventSkillGroupList;
    }
}
