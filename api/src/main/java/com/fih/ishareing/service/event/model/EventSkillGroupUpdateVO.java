package com.fih.ishareing.service.event.model;

import javax.validation.constraints.NotNull;

public class EventSkillGroupUpdateVO {
    @NotNull
    private Integer id;
    private String name;
    private String skillsDescription;
    private Integer volunteerNumber;

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

    public String getSkillsDescription() {
        return skillsDescription;
    }

    public void setSkillsDescription(String skillsDescription) {
        this.skillsDescription = skillsDescription;
    }

    public Integer getVolunteerNumber() {
        return volunteerNumber;
    }

    public void setVolunteerNumber(Integer volunteerNumber) {
        this.volunteerNumber = volunteerNumber;
    }
}
