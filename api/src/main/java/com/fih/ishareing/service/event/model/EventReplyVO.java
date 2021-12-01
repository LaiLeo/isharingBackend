package com.fih.ishareing.service.event.model;

import com.fih.ishareing.repository.criteria.annotations.QueryCondition;

import java.sql.Timestamp;

public class EventReplyVO {
    private Integer id;
    private Integer userId;
    private String userName;
    private String userPhoto;
    private String userIcon;
    private String message;
    private String image;
    private String thumbPath;
    private Timestamp replyTime;

    public EventReplyVO() {
    }

    public EventReplyVO(Integer id, Integer userId, String userName, String userPhoto, String userIcon, String message, String image, String thumbPath, Timestamp replyTime) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.userPhoto = userPhoto;
        this.userIcon = userIcon;
        this.message = message;
        this.image = image;
        this.thumbPath = thumbPath;
        this.replyTime = replyTime;
    }

    @QueryCondition(searchable = false, sortable = false, field = "id", databaseField = "id")
    public Integer getId() {
        return id;
    }

    @QueryCondition(searchable = false, sortable = false, field = "image", databaseField = "image")
    public String getImage() {
        return image;
    }

    @QueryCondition(searchable = false, sortable = false, field = "thumbPath", databaseField = "thumbPath")
    public String getThumbPath() {
        return thumbPath;
    }

    @QueryCondition(searchable = true, sortable = false, field = "replyTime", databaseField = "replyTime")
    public Timestamp getReplyTime() {
        return replyTime;
    }

    @QueryCondition(searchable = true, sortable = false, field = "userId", databaseField = "userId")
    public Integer getUserId() {
        return userId;
    }
    @QueryCondition(searchable = true, sortable = false, field = "userName", databaseField = "userName")
    public String getUserName() {
        return userName;
    }

    @QueryCondition(searchable = false, sortable = false, field = "userName", databaseField = "userName")
    public String getUserPhoto() {
        return userPhoto;
    }

    @QueryCondition(searchable = false, sortable = false, field = "userName", databaseField = "userName")
    public String getUserIcon() {
        return userIcon;
    }

    public String getMessage() {
        return message;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public void setReplyTime(Timestamp replyTime) {
        this.replyTime = replyTime;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
