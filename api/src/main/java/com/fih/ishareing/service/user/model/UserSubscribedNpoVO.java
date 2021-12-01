package com.fih.ishareing.service.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fih.ishareing.repository.criteria.annotations.QueryCondition;

import java.sql.Timestamp;

public class UserSubscribedNpoVO {
    private Integer userId;
    private Integer npoId;
    private String npoName;
    private String npoUid;
    private String npoIcon;
    private Integer ratingUserNum;
    private Integer totalRatingScore;
    private Integer subscribedUserNum;
    private Integer joinedUserNum;
    private Integer eventNum;
    @JsonProperty("isVerified")
    private Boolean verified;
    @JsonIgnore
    private Boolean admViewed;
    private Timestamp focusedDate;

    @QueryCondition(searchable = true, sortable = true, field = "npoId", databaseField = "npoId")
    public Integer getNpoId() {
        return npoId;
    }

    public void setNpoId(Integer npoId) {
        this.npoId = npoId;
    }

    @QueryCondition(searchable = true, sortable = true, field = "userId", databaseField = "userId")
    public Integer getUserId() {
        return userId;
    }

    @QueryCondition(searchable = true, sortable = true, field = "npoName", databaseField = "npoName")
    public String getNpoName() {
        return npoName;
    }

    @QueryCondition(searchable = true, sortable = false, field = "npoUid", databaseField = "npoUid")
    public String getNpoUid() {
        return npoUid;
    }

    public String getNpoIcon() {
        return npoIcon;
    }

    @QueryCondition(searchable = true, sortable = true, field = "ratingUserNum", databaseField = "ratingUserNum")
    public Integer getRatingUserNum() {
        return ratingUserNum;
    }
    @QueryCondition(searchable = true, sortable = true, field = "totalRatingScore", databaseField = "totalRatingScore")
    public Integer getTotalRatingScore() {
        return totalRatingScore;
    }
    @QueryCondition(searchable = true, sortable = true, field = "subscribedUserNum", databaseField = "subscribedUserNum")
    public Integer getSubscribedUserNum() {
        return subscribedUserNum;
    }
    @QueryCondition(searchable = true, sortable = true, field = "joinedUserNum", databaseField = "joinedUserNum")
    public Integer getJoinedUserNum() {
        return joinedUserNum;
    }
    @QueryCondition(searchable = true, sortable = true, field = "eventNum", databaseField = "eventNum")
    public Integer getEventNum() {
        return eventNum;
    }
    @QueryCondition(searchable = true, sortable = false, field = "isVerified", databaseField = "isVerified")
    public Boolean getVerified() {
        return this.verified;
    }
    @QueryCondition(searchable = true, sortable = false, field = "admViewed", databaseField = "admViewed")
    public Boolean getAdmViewed() {
        return admViewed;
    }
    @QueryCondition(searchable = true, sortable = true, field = "focusedDate", databaseField = "focusedDate")
    public Timestamp getFocusedDate() {
        return focusedDate;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setNpoName(String npoName) {
        this.npoName = npoName;
    }

    public void setNpoUid(String npoUid) {
        this.npoUid = npoUid;
    }

    public void setNpoIcon(String npoIcon) {
        this.npoIcon = npoIcon;
    }

    public void setRatingUserNum(Integer ratingUserNum) {
        this.ratingUserNum = ratingUserNum;
    }

    public void setTotalRatingScore(Integer totalRatingScore) {
        this.totalRatingScore = totalRatingScore;
    }

    public void setSubscribedUserNum(Integer subscribedUserNum) {
        this.subscribedUserNum = subscribedUserNum;
    }

    public void setJoinedUserNum(Integer joinedUserNum) {
        this.joinedUserNum = joinedUserNum;
    }

    public void setEventNum(Integer eventNum) {
        this.eventNum = eventNum;
    }

    public void setFocusedDate(Timestamp focusedDate) {
        this.focusedDate = focusedDate;
    }
}
