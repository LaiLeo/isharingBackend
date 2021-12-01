package com.fih.ishareing.service.appConfig.model;

import javax.validation.constraints.NotNull;

public class AppConfigUpdateVO {
    @NotNull
    private Integer id;
    private String iosVersion;
    private String androidVersion;
    private Boolean forcedUpgrade;
    private String questionnaireUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIosVersion() {
        return iosVersion;
    }

    public void setIosVersion(String iosVersion) {
        this.iosVersion = iosVersion;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public Boolean getForcedUpgrade() {
        return forcedUpgrade;
    }

    public void setForcedUpgrade(Boolean forcedUpgrade) {
        this.forcedUpgrade = forcedUpgrade;
    }

    public String getQuestionnaireUrl() {
        return questionnaireUrl;
    }

    public void setQuestionnaireUrl(String questionnaireUrl) {
        this.questionnaireUrl = questionnaireUrl;
    }
}
