package com.fih.ishareing.repository.entity;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the third_twmEnterpriseInfo database table.
 * 
 */
@Entity
@Table(name = "third_twmEnterpriseInfo")
public class tbThirdTwmEnterpriseInfo implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="enterpriseSerialNumber")
	private String enterpriseSerialNumber;

	@Column(name="enterpriseSerialId")
	private String enterpriseSerialId;

	@Column(name="enterpriseSerialPhone")
	private String enterpriseSerialPhone;

	@Column(name="enterpriseSerialDepartment")
	private String enterpriseSerialDepartment;

	@Column(name="enterpriseSerialType")
	private String enterpriseSerialType;

	@Column(name="enterpriseSerialGroup")
	private String enterpriseSerialGroup;

	public tbThirdTwmEnterpriseInfo() {
	}

	public tbThirdTwmEnterpriseInfo(String enterpriseSerialNumber, String enterpriseSerialId, String enterpriseSerialPhone, String enterpriseSerialDepartment, String enterpriseSerialType, String enterpriseSerialGroup) {
		this.enterpriseSerialNumber = enterpriseSerialNumber;
		this.enterpriseSerialId = enterpriseSerialId;
		this.enterpriseSerialPhone = enterpriseSerialPhone;
		this.enterpriseSerialDepartment = enterpriseSerialDepartment;
		this.enterpriseSerialType = enterpriseSerialType;
		this.enterpriseSerialGroup = enterpriseSerialGroup;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEnterpriseSerialNumber() {
		return enterpriseSerialNumber;
	}

	public void setEnterpriseSerialNumber(String enterpriseSerialNumber) {
		this.enterpriseSerialNumber = enterpriseSerialNumber;
	}

	public String getEnterpriseSerialId() {
		return enterpriseSerialId;
	}

	public void setEnterpriseSerialId(String enterpriseSerialId) {
		this.enterpriseSerialId = enterpriseSerialId;
	}

	public String getEnterpriseSerialPhone() {
		return enterpriseSerialPhone;
	}

	public void setEnterpriseSerialPhone(String enterpriseSerialPhone) {
		this.enterpriseSerialPhone = enterpriseSerialPhone;
	}

	public String getEnterpriseSerialDepartment() {
		return enterpriseSerialDepartment;
	}

	public void setEnterpriseSerialDepartment(String enterpriseSerialDepartment) {
		this.enterpriseSerialDepartment = enterpriseSerialDepartment;
	}

	public String getEnterpriseSerialType() {
		return enterpriseSerialType;
	}

	public void setEnterpriseSerialType(String enterpriseSerialType) {
		this.enterpriseSerialType = enterpriseSerialType;
	}

	public String getEnterpriseSerialGroup() {
		return enterpriseSerialGroup;
	}

	public void setEnterpriseSerialGroup(String enterpriseSerialGroup) {
		this.enterpriseSerialGroup = enterpriseSerialGroup;
	}
}