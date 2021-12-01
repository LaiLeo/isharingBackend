package com.fih.ishareing.service.banner.model;

import com.fih.ishareing.repository.criteria.annotations.QueryCondition;

public class BannerVO{
    private Integer id;
    private String url;
    private String image;
    private Integer displaySort;
    private String smallImage;


    @QueryCondition(searchable = true, sortable = true, field = "id", databaseField = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @QueryCondition(searchable = false, sortable = false, field = "url", databaseField = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @QueryCondition(searchable = false, sortable = false, field = "image", databaseField = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @QueryCondition(searchable = false, sortable = true, field = "displaySort", databaseField = "displaySort")
    public Integer getDisplaySort() {
        return displaySort;
    }

    public void setDisplaySort(Integer displaySort) {
        this.displaySort = displaySort;
    }

    @QueryCondition(searchable = false, sortable = false, field = "smallImage", databaseField = "smallImage")
    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }
}
