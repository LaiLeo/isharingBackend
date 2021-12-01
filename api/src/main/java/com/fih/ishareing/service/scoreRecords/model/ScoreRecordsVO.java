package com.fih.ishareing.service.scoreRecords.model;

import com.fih.ishareing.repository.criteria.annotations.QueryCondition;

import java.sql.Timestamp;

public class ScoreRecordsVO {
    private Integer id;
    private String eventName;
    private Integer eventId;
    private Integer userId;
    private Integer score;
    private String comment;
    private Timestamp addDate;


    @QueryCondition(searchable = true, sortable = true, field = "id", databaseField = "id")
    public Integer getId() {
        return id;
    }

    @QueryCondition(searchable = true, sortable = true, field = "eventName", databaseField = "eventName")
    public String getEventName() {
        return eventName;
    }
    @QueryCondition(searchable = true, sortable = true, field = "eventId", databaseField = "eventId")
    public Integer getEventId() {
        return eventId;
    }
    @QueryCondition(searchable = true, sortable = true, field = "userId", databaseField = "userId")
    public Integer getUserId() {
        return userId;
    }
    @QueryCondition(searchable = true, sortable = true, field = "score", databaseField = "score")
    public Integer getScore() {
        return score;
    }
    @QueryCondition(searchable = true, sortable = true, field = "comment", databaseField = "comment")
    public String getComment() {
        return comment;
    }
    @QueryCondition(searchable = true, sortable = true, field = "addDate", databaseField = "addDate")
    public Timestamp getAddDate() {
        return addDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setAddDate(Timestamp addDate) {
        this.addDate = addDate;
    }
}
