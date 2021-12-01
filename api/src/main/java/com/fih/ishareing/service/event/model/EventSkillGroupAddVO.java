package com.fih.ishareing.service.event.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EventSkillGroupAddVO {
    @NotNull
    private Integer eventId;
    @NotNull
    @Size(max = 100)
    private String name;
    @NotNull
    @Size(max = 1024)
    private String skillsDescription;
    @NotNull
    @Min(value = 0)
    private Integer volunteerNumber;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
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
