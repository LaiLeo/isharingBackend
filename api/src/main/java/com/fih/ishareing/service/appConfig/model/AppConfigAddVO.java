package com.fih.ishareing.service.appConfig.model;

public class AppConfigAddVO {
    private String iosVersion;
    private String androidVersion;
    private Boolean forcedUpgrade;
    private String questionnaireUrl;

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
