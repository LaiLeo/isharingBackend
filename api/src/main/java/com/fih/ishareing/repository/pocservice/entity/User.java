package com.fih.ishareing.repository.pocservice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name = "users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private UUID code;
	private String account;
	private Boolean active;
	private Boolean adminUser;
	private String applicationCode;
	private Date createdTime;
	private Integer createdUserId;
	private String email;
	private String phone;
	private Boolean enable;
	private Boolean forceResetPassword;
	private Date lastUpdatePasswordTime;
	private Date modifiedTime;
	private Integer modifiedUserId;
	private String name;
	private String password;
	private Boolean suUser;
	private Boolean validated;

	public User() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(nullable = false)
	public UUID getCode() {
		return this.code;
	}

	public void setCode(UUID code) {
		this.code = code;
	}

	@Column(nullable = false, length = 20)
	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(nullable = false)
	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Column(name = "admin_user", nullable = false)
	public Boolean getAdminUser() {
		return this.adminUser;
	}

	public void setAdminUser(Boolean adminUser) {
		this.adminUser = adminUser;
	}

	@Column(name = "application_code", nullable = false, length = 20)
	public String getApplicationCode() {
		return this.applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time", nullable = false)
	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Column(name = "created_user_id", nullable = false)
	public Integer getCreatedUserId() {
		return this.createdUserId;
	}

	public void setCreatedUserId(Integer createdUserId) {
		this.createdUserId = createdUserId;
	}

	@Column(nullable = false, length = 64)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(nullable = true, length = 64)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(nullable = false)
	public Boolean getEnable() {
		return this.enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	@Column(name = "force_reset_password")
	public Boolean getForceResetPassword() {
		return this.forceResetPassword;
	}

	public void setForceResetPassword(Boolean forceResetPassword) {
		this.forceResetPassword = forceResetPassword;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update_password_time")
	public Date getLastUpdatePasswordTime() {
		return this.lastUpdatePasswordTime;
	}

	public void setLastUpdatePasswordTime(Date lastUpdatePasswordTime) {
		this.lastUpdatePasswordTime = lastUpdatePasswordTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_time", nullable = false)
	public Date getModifiedTime() {
		return this.modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	@Column(name = "modified_user_id", nullable = false)
	public Integer getModifiedUserId() {
		return this.modifiedUserId;
	}

	public void setModifiedUserId(Integer modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}

	@Column(nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false, length = 256)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "su_user", nullable = false)
	public Boolean getSuUser() {
		return this.suUser;
	}

	public void setSuUser(Boolean suUser) {
		this.suUser = suUser;
	}

	@Column(nullable = false)
	public Boolean getValidated() {
		return this.validated;
	}

	public void setValidated(Boolean validated) {
		this.validated = validated;
	}

}