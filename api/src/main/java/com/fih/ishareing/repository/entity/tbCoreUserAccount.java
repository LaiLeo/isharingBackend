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
 * The persistent class for the core_useraccount database table.
 * 
 */
@Entity
@Table(name = "core_useraccount")
public class tbCoreUserAccount implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="user_id")
	private Integer userId;
	
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
	
	@Column(name="email")
	private String email;
	
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
	private Integer isPublic;

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

	public tbCoreUserAccount()
	{
		String strBirthday = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		
		this.setUid(UUID.randomUUID().toString());
		this.setName("");
		this.setPhone("");
		this.setSecurityId("");
		this.setEmail("");
		this.setSkillsDescription("");
		this.setBirthday(strBirthday);
		this.setGuardianName("");
		this.setGuardianPhone("");
		this.setIcon("");
		this.setIsPublic(1);
		this.setInterest("");
		this.setAboutMe("");
		this.setScore(0);
		this.setRanking(0);
		this.setEventHour(0.0);
		this.setEventNum(0);
		this.setEventEnterpriseHour(0.0);
		this.setEventGeneralHour(0.0);
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Integer getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
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
	


}