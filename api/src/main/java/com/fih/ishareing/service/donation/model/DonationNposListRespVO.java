package com.fih.ishareing.service.donation.model;

public class DonationNposListRespVO {

	private Integer id;
	private String name;
	private String description;
	private String code;
	private String newebpayUrl;
	private String npoIcon;
	private String thumbPath;
	private String newebpayPeriodUrl;
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
	public String getNpoIcon() {
		return npoIcon;
	}
	public void setNpoIcon(String npoIcon) {
		this.npoIcon = npoIcon;
	}
	public String getThumbPath() {
		return thumbPath;
	}
	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
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
