package com.fih.ishareing.repository.entity;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the core_user_third database table.
 * 
 */
@Entity
@Table(name = "core_user_third")
public class tbCoreUserThird implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="user_id")
	private Integer userId;

	@Column(name="enterpriseSerialCode")
	private String enterpriseSerialCode;

	@Column(name="enterpriseSerialNumber")
	private String enterpriseSerialNumber;

	@Column(name="enterpriseSerialEmail")
	private String enterpriseSerialEmail;


	public tbCoreUserThird() {
	}

	public tbCoreUserThird(Integer userId, String enterpriseSerialCode, String enterpriseSerialNumber, String enterpriseSerialEmail) {
		this.userId = userId;
		this.enterpriseSerialCode = enterpriseSerialCode;
		this.enterpriseSerialNumber = enterpriseSerialNumber;
		this.enterpriseSerialEmail = enterpriseSerialEmail;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEnterpriseSerialCode() {
		return enterpriseSerialCode;
	}

	public void setEnterpriseSerialCode(String enterpriseSerialCode) {
		this.enterpriseSerialCode = enterpriseSerialCode;
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