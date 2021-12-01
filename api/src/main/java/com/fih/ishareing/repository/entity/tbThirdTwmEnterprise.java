package com.fih.ishareing.repository.entity;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the third_twmEnterprise database table.
 * 
 */
@Entity
@Table(name = "third_twmEnterprise")
public class tbThirdTwmEnterprise implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="enterpriseSerialName")
	private String enterpriseSerialName;

	@Column(name="enterpriseSerialNumber")
	private String enterpriseSerialNumber;

	@Column(name="enterpriseSerialEmail")
	private String enterpriseSerialEmail;


	public tbThirdTwmEnterprise() {
	}

	public tbThirdTwmEnterprise(String enterpriseSerialName, String enterpriseSerialNumber, String enterpriseSerialEmail) {
		this.enterpriseSerialName = enterpriseSerialName;
		this.enterpriseSerialNumber = enterpriseSerialNumber;
		this.enterpriseSerialEmail = enterpriseSerialEmail;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEnterpriseSerialName() {
		return enterpriseSerialName;
	}

	public void setEnterpriseSerialName(String enterpriseSerialName) {
		this.enterpriseSerialName = enterpriseSerialName;
	}

	public String getEnterpriseSerialNumber() {
		return enterpriseSerialNumber;
	}

	public void setEnterpriseSerialNumber(String enterpriseSerialNumber) {
		this.enterpriseSerialNumber = enterpriseSerialNumber;
	}

	public String getEnterpriseSerialEmail() {
		return enterpriseSerialEmail;
	}

	public void setEnterpriseSerialEmail(String enterpriseSerialEmail) {
		this.enterpriseSerialEmail = enterpriseSerialEmail;
	}
}