package com.fih.ishareing.service.user.model;

import com.fih.ishareing.repository.criteria.annotations.QueryCondition;

public class UserLicenseImagesVO {
    private Integer userId;
    private Integer licenseId;
    private String image;
    private String thumbPath;

    public UserLicenseImagesVO() {
    }

    public UserLicenseImagesVO(Integer userId, Integer licenseId, String image, String thumbPath) {
        this.userId = userId;
        this.licenseId = licenseId;
        this.image = image;
        this.thumbPath = thumbPath;
    }

    @QueryCondition(searchable = true, sortable = false, field = "userId", databaseField = "userId")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @QueryCondition(searchable = true, sortable = false, field = "licenseId", databaseField = "id")
    public Integer getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(Integer licenseId) {
        this.licenseId = licenseId;
    }

    @QueryCondition(searchable = false, sortable = false, field = "thumbPath", databaseField = "thumbPath")
    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    @QueryCondition(searchable = false, sortable = false, field = "image", databaseField = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
