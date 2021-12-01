package com.fih.ishareing.service.event.model;

import com.fih.ishareing.repository.criteria.annotations.QueryCondition;

public class EventSkillGroupVO {
    private Integer id;
    private String name;
    private String skillsDescription;
    private Integer volunteerNumber;
    private Integer currentVolunteerNumber;

    public EventSkillGroupVO() {
    }

    public EventSkillGroupVO(Integer id, String name, String skillsDescription, Integer volunteerNumber, Integer currentVolunteerNumber) {
        this.id = id;
        this.name = name;
        this.skillsDescription = skillsDescription;
        this.volunteerNumber = volunteerNumber;
        this.currentVolunteerNumber = currentVolunteerNumber;
    }

    @QueryCondition(searchable = true, sortable = false, field = "id", databaseField = "id")
    public Integer getId() {
        return id;
    }

    @QueryCondition(searchable = true, sortable = false, field = "name", databaseField = "name")
    public String getName() {
        return name;
    }

    @QueryCondition(searchable = false, sortable = false, field = "skillsDescription", databaseField = "skillsDescription")
    public String getSkillsDescription() {
        return skillsDescription;
    }

    @QueryCondition(searchable = true, sortable = false, field = "volunteerNumber", databaseField = "volunteerNumber")
    public Integer getVolunteerNumber() {
        return volunteerNumber;
    }

    @QueryCondition(searchable = true, sortable = false, field = "currentVolunteerNumber", databaseField = "currentVolunteerNumber")
    public Integer getCurrentVolunteerNumber() {
        return currentVolunteerNumber;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSkillsDescription(String skillsDescription) {
        this.skillsDescription = skillsDescription;
    }

    public void setVolunteerNumber(Integer volunteerNumber) {
        this.volunteerNumber = volunteerNumber;
    }

    public void setCurrentVolunteerNumber(Integer currentVolunteerNumber) {
        this.currentVolunteerNumber = currentVolunteerNumber;
    }
}
