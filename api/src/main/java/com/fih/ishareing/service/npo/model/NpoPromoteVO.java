package com.fih.ishareing.service.npo.model;

import com.fih.ishareing.repository.criteria.annotations.QueryCondition;

public class NpoPromoteVO {
    private Integer id;
    private String name;

    @QueryCondition(searchable = true, sortable = true, field = "id", databaseField = "id")
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    @QueryCondition(searchable = true, sortable = true, field = "name", databaseField = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
