package com.fih.ishareing.repository.entity;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the core_appConfig database table.
 * 
 */
@Entity
@Table(name = "core_appConfig")
public class tbCoreAppConfig implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="iosVersion")
	private String iosVersion;

	@Column(name="androidVersion")
	private String androidVersion;

	@Column(name="forcedUpgrade")
	private Boolean forcedUpgrade;

	@Column(name="questionnaireUrl")
	private String questionnaireUrl;

	public tbCoreAppConfig() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIosVersion() {
		return iosVersion;
	}

	public void setIosVersion(String iosVersion) {
		this.iosVersion = iosVersion;
	}

	public String getAndroidVersion() {
		return androidVersion;
	}

	public void setAndroidVersion(String androidVersion) {
		this.androidVersion = androidVersion;
	}

	public Boolean getForcedUpgrade() {
		return forcedUpgrade;
	}

	public void setForcedUpgrade(Boolean forcedUpgrade) {
		this.forcedUpgrade = forcedUpgrade;
	}

	public String getQuestionnaireUrl() {
		return questionnaireUrl;
	}

	public void setQuestionnaireUrl(String questionnaireUrl) {
		this.questionnaireUrl = questionnaireUrl;
	}
}