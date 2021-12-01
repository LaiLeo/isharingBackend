package com.fih.ishareing.repository.entity;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the core_npo database table.
 * 
 */
@Entity
@Table(name = "core_npo")
public class vwCoreNpoMenu implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="name")
	private String name;

	@Column(name="contact_email")
	private String contactEmail;

	@Column(name="is_verified")
	private Boolean verified;

	@Column(name="adm_viewed")
	private Boolean admViewed;


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

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public Boolean getAdmViewed() {
		return admViewed;
	}

	public void setAdmViewed(Boolean admViewed) {
		this.admViewed = admViewed;
	}
}