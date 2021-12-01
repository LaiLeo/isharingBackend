package com.fih.ishareing.repository.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fih.ishareing.utils.date.DateUtils;



/**
 * The persistent class for the core_donationnpo database table.
 * 
 */
@Entity
@Table(name = "core_donationnpo")
public class tbCoreDonationNpo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="npo_icon")
	private String npoIcon;

	@Column(name="thumb_path")
	private String thumbPath;

	@Column(name="description")
	private String description;
	
	@Column(name="code")
	private String code;
	
	@Column(name="newebpay_url")
	private String newebpayUrl;

	@Column(name="newebpayPeriodUrl")
	private String newebpayPeriodUrl;

	@Column(name="displaySort")
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