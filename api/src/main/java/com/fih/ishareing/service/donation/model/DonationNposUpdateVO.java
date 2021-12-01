package com.fih.ishareing.service.donation.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DonationNposUpdateVO {
    @NotNull
    private Integer id;
    @Size(max = 300)
    private String name;
    @Size(max = 1000)
    private String description;
    @Size(max = 300)
    private String code;
    @Size(max = 300)
    private String newebpayUrl;
    private String newebpayPeriodUrl;
    @NotNull
    private Integer displaySort;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNewebpayUrl() {
        return newebpayUrl;
    }

    public void setNewebpayUrl(String newebpayUrl) {
        this.newebpayUrl = newebpayUrl;
    }

    public String getNewebpayPeriodUrl() {
        return newebpayPeriodUrl;
    }

    public void setNewebpayPeriodUrl(String newebpayPeriodUrl) {
        this.newebpayPeriodUrl = newebpayPeriodUrl;
    }

    public Integer getDisplaySort() {
        return displaySort;
    }

    public void setDisplaySort(Integer displaySort) {
        this.displaySort = displaySort;
    }
}
