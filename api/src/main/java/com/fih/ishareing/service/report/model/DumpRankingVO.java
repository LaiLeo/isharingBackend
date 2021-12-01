package com.fih.ishareing.service.report.model;

public class DumpRankingVO {
    private Integer ranking;
    private String email;
    private String name;
    private String phone;
    private String eventNames;

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
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

    public String getEventNames() {
        return eventNames;
    }

    public void setEventNames(String eventNames) {
        this.eventNames = eventNames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
