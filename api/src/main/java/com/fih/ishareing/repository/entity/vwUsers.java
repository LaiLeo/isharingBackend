package com.fih.ishareing.repository.entity;

import org.hibernate.annotations.BatchSize;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.*;


/**
 * The persistent class for the vw_users database table.
 * 
 */
@Entity
@Table(name = "vw_users")
public class vwUsers implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="last_login")
	private Timestamp lastLogin;

	@Column(name="username")
	private String username;
	
	@Column(name="first_name")
	private String firstname;
	
	@Column(name="last_name")
	private String lastname;
	
	@Column(name="email")
	private String email;
	
	@Column(name="is_staff")
	private Boolean isStaff;

	@Column(name="is_active")
	private Integer isActive;

	@Column(name="date_joined")
	private Timestamp dateJoined;

	@Column(name="uid")
	private String uid;

	@Column(name="photo")
	private String photo;

	@Column(name="name")
	private String name;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="security_id")
	private String securityId;
	
	@Column(name="skills_description")
	private String skillsDescription;

	@Column(name="birthday")
	private String birthday;

	@Column(name="guardian_name")
	private String guardianName;

	@Column(name="guardian_phone")
	private String guardianPhone;

	@Column(name="icon")
	private String icon;

	@Column(name="thumb_path")
	private String thumbPath;

	@Column(name="is_public")
	private Boolean isPublic;

	@Column(name="interest")
	private String interest;

	@Column(name="about_me")
	private String aboutMe;

	@Column(name="score")
	private Integer score;

	@Column(name="ranking")
	private Integer ranking;

	@Column(name="event_hour")
	private Double eventHour;

	@Column(name="event_num")
	private Integer eventNum;

	@Column(name="event_enterprise_hour")
	private Double eventEnterpriseHour;

	@Column(name="event_general_hour")
	private Double eventGeneralHour;

	@Column(name="is_npo")
	private Boolean isNpo;

	@Column(name="npoId")
	private Integer npoId;

	@Column(name="is_fubon")
	private Boolean isFubon;

	@Column(name="is_twm")
	private Boolean isTwm;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@BatchSize(size = 100)
	private List<tbCoreUserLicenseImage> userLicenseImageList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public Timestamp getDateJoined() {
		return dateJoined;
	}

	public void setDateJoined(Timestamp dateJoined) {
		this.dateJoined = dateJoined;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public String getSkillsDescription() {
		return skillsDescription;
	}

	public void setSkillsDescription(String skillsDescription) {
		this.skillsDescription = skillsDescription;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGuardianName() {
		return guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	public String getGuardianPhone() {
		return guardianPhone;
	}

	public void setGuardianPhone(String guardianPhone) {
		this.guardianPhone = guardianPhone;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getThumbPath() {
		return thumbPath;
	}

	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}

	public Boolean getPublic() {
		return isPublic;
	}

	public void setPublic(Boolean aPublic) {
		isPublic = aPublic;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public Double getEventHour() {
		return eventHour;
	}

	public void setEventHour(Double eventHour) {
		this.eventHour = eventHour;
	}

	public Integer getEventNum() {
		return eventNum;
	}

	public void setEventNum(Integer eventNum) {
		this.eventNum = eventNum;
	}

	public Double getEventEnterpriseHour() {
		return eventEnterpriseHour;
	}

	public void setEventEnterpriseHour(Double eventEnterpriseHour) {
		this.eventEnterpriseHour = eventEnterpriseHour;
	}

	public Double getEventGeneralHour() {
		return eventGeneralHour;
	}

	public void setEventGeneralHour(Double eventGeneralHour) {
		this.eventGeneralHour = eventGeneralHour;
	}

	public Boolean getStaff() {
		return isStaff;
	}

	public void setStaff(Boolean staff) {
		isStaff = staff;
	}

	public Boolean getNpo() {
		return isNpo;
	}

	public void setNpo(Boolean npo) {
		isNpo = npo;
	}

	public Integer getNpoId() {
		return npoId;
	}

	public void setNpoId(Integer npoId) {
		this.npoId = npoId;
	}

	public Boolean getFubon() {
		return isFubon;
	}

	public void setFubon(Boolean fubon) {
		isFubon = fubon;
	}

	public Boolean getTwm() {
		return isTwm;
	}

	public void setTwm(Boolean twm) {
		isTwm = twm;
	}

	public List<tbCoreUserLicenseImage> getUserLicenseImageList() {
		return userLicenseImageList;
	}

	public void setUserLicenseImageList(List<tbCoreUserLicenseImage> userLicenseImageList) {
		this.userLicenseImageList = userLicenseImageList;
	}
}