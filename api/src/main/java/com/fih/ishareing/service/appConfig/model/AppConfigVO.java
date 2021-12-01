package com.fih.ishareing.service.appConfig.model;

import com.fih.ishareing.repository.criteria.annotations.QueryCondition;

public class AppConfigVO {
    private Integer id;
    private String iosVersion;
    private String androidVersion;
    private Boolean forcedUpgrade;
    private String questionnaireUrl;


    @QueryCondition(searchable = true, sortable = true, field = "id", databaseField = "id")
    public Integer getId() {
        return id;
    }

    @QueryCondition(searchable = true, sortable = true, field = "iosVersion", databaseField = "iosVersion")
    public String getIosVersion() {
        return iosVersion;
    }

    @QueryCondition(searchable = true, sortable = true, field = "androidVersion", databaseField = "androidVersion")
    public String getAndroidVersion() {
        return androidVersion;
    }

    @QueryCondition(searchable = true, sortable = true, field = "forcedUpgrade", databaseField = "forcedUpgrade")
    public Boolean getForcedUpgrade() {
        return forcedUpgrade;
    }

    @QueryCondition(searchable = false, sortable = false, field = "questionnaireUrl", databaseField = "questionnaireUrl")
    public String getQuestionnaireUrl() {
        return questionnaireUrl;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setIosVersion(String iosVersion) {
        this.iosVersion = iosVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public void setForcedUpgrade(Boolean forcedUpgrade) {
        this.forcedUpgrade = forcedUpgrade;
    }

    public void setQuestionnaireUrl(String questionnaireUrl) {
        this.questionnaireUrl = questionnaireUrl;
    }
}
