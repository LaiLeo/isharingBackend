package com.fih.ishareing.service.event.model;

import com.fih.ishareing.repository.criteria.annotations.QueryCondition;

public class EventResultImageVO {
    private Integer id;
    private String image;
    private String thumbPath;
    private Integer displaySort;

    public EventResultImageVO() {
    }

    public EventResultImageVO(Integer id, String image, String thumbPath, Integer displaySort) {
        this.id = id;
        this.image = image;
        this.thumbPath = thumbPath;
        this.displaySort = displaySort;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public Integer getDisplaySort() {
        return displaySort;
    }

    public void setDisplaySort(Integer displaySort) {
        this.displaySort = displaySort;
    }
}
