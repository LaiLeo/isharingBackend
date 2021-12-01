
drop view if exists vw_userfocusedevent;
CREATE VIEW vw_userfocusedevent AS
SELECT CONCAT(cf.user_id, cf.focused_event_id) ID, cf.user_id UserId, cf.focused_event_id EventID, ve.owner_NPO_id NpoID , ve.image_link_1 Image1, ve.uid Uid, ve.happen_date HappenDate,
ve.subject Subject, ve.required_volunteer_number ReqVolunteerNumer, ve.current_volunteer_number CurrVolunteeNumber, ve.thumb_path ThumbPath,
1 IsFocus, CASE WHEN cu.id IS NOT NULL THEN 1 ELSE 0 END IsRegister, ve.is_volunteer_event, ve.is_enterprise, ve.pub_date pubDate
FROM core_userfocusedevent cf
LEFT JOIN vw_event ve ON cf.focused_event_id = ve.id
LEFT JOIN core_userregisteredevent cu ON cf.user_id = cu.user_id AND cf.focused_event_id = cu.registered_event_id
ORDER BY UserId, EventID;

drop view if exists vw_userregisteredevent;
CREATE VIEW vw_userregisteredevent AS
SELECT CONCAT(cu.user_id, cu.registered_event_id) ID, cu.user_id UserId, cu.registered_event_id EventID, ve.owner_NPO_id NpoID , ve.image_link_1 Image1, ve.uid Uid, ve.happen_date HappenDate,
ve.subject Subject, ve.required_volunteer_number ReqVolunteerNumer, ve.current_volunteer_number CurrVolunteeNumber, ve.thumb_path ThumbPath,
1 IsRegister, CASE WHEN cu2.id IS NOT NULL THEN 1 ELSE 0 END IsFocus, ve.is_volunteer_event, ve.is_enterprise, cu.isJoined, cu.score,
cu.name as registeredName, cu.phone as registeredPhone, cu.email as registeredEmail, cu.skills as registeredSkills, cu.birthday as registeredBirthday,
cu.guardian_name as registeredGuardianName, cu.guardian_phone as guardianPhone, cu.security_id as securityId, cu.note,
cu.employee_serial_number as employeeSerialNumber,cu.enterprise_serial_number as enterpriseSerialNumber, cu.isLeaved, cu.join_time as joinTime, cu.leave_time as leaveTime,
cu.register_date as registerDate, cut.enterpriseSerialNumber as thirdEnterpriseSerialNumber, u.score as userScore, ve.pub_date pubDate, ve.event_hour as eventHour, ve.close_date as closeDate
FROM core_userregisteredevent cu
LEFT JOIN vw_event ve ON cu.registered_event_id = ve.id
LEFT JOIN core_userfocusedevent cu2 ON cu.user_id = cu2.user_id AND cu.registered_event_id = cu2.focused_event_id
LEFT JOIN core_user_third cut on cu.user_id = cut.user_id
LEFT JOIN core_useraccount u on cu.user_id = u.id
ORDER BY UserId, EventID;


ALTER TABLE core_eventresultimage ADD COLUMN displaySort int(5) default null;
ALTER TABLE core_usersubscribednpo ADD COLUMN focusedDate datetime default null;
ALTER TABLE core_banner ADD COLUMN smallImage varchar(100) default null;
ALTER TABLE core_event ADD COLUMN note text default null;

drop view if exists vw_userSubscribedNpos;
CREATE VIEW vw_userSubscribedNpos AS
SELECT CONCAT(us.user_id, n.id) ID, us.user_id as UserId, n.id as npoId, n.name as npoName, n.uid as npoUid, n.npo_icon as npoIcon,
n.rating_user_num as ratingUserNum, n.total_rating_score as totalRatingScore, n.subscribed_user_num as subscribedUserNum,
n.joined_user_num as joinedUserNum, n.event_num as eventNum, n.is_verified as isVerified, n.adm_viewed as admViewed, n.administrator_id as admId,
us.focusedDate
FROM core_usersubscribednpo us
LEFT JOIN core_npo n ON us.subscribed_NPO_id = n.id;

drop view if exists vw_event;
CREATE VIEW vw_event AS
select e.id,e.owner_NPO_id,e.image_link_1,e.image_link_2,e.image_link_3,e.image_link_4,e.image_link_5,e.uid,e.tags,e.pub_date
     ,e.happen_date,e.close_date,e.register_deadline_date,e.subject,e.description,e.event_hour,e.focus_num,e.address_city
     ,e.address,e.insurance,e.insurance_description,e.volunteer_training,e.volunteer_training_description,e.lat,e.lng
     ,e.required_volunteer_number,e.current_volunteer_number,e.required_group,e.skills_description,e.user_account_id
     ,e.thumb_path,e.reply_num,e.rating_user_num,e.total_rating_score,e.is_volunteer_event,e.is_urgent,e.leave_uid
     ,e.require_signout,e.is_short,e.volunteer_type,e.donation_serial,e.donation_start_date,e.donation_end_date
     ,e.service_type,e.foreign_third_party_id,n.name as npoName,
    case when e.required_volunteer_number - current_volunteer_number = 0 then '1' else '0' end as isFull,
    n.is_enterprise, e.promote, e.note
from core_event e
         left join core_npo n on e.owner_npo_id = n.id
order by e.is_Urgent desc;

DROP TABLE IF EXISTS `third_twmEnterprise`;
CREATE TABLE `third_twmEnterprise` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `enterpriseSerialName` varchar(50) NOT NULL,
  `enterpriseSerialNumber` varchar(20) NOT NULL,
  `enterpriseSerialEmail` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `third_twmEnterpriseInfo`;
CREATE TABLE `third_twmEnterpriseInfo` (
   `id` int(20) NOT NULL AUTO_INCREMENT,
   `enterpriseSerialNumber` varchar(20) NOT NULL,
   `enterpriseSerialId` varchar(20) NOT NULL,
   `enterpriseSerialPhone` varchar(20) NOT NULL,
   `enterpriseSerialDepartment` varchar(100) NOT NULL,
   `enterpriseSerialType` varchar(50) NOT NULL,
   `enterpriseSerialGroup` varchar(50) NOT NULL,
   PRIMARY KEY (`id`, `enterpriseSerialNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `core_appConfig`;
CREATE TABLE `core_appConfig` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `iosVersion` varchar(20) NULL COMMENT 'ios version',
  `androidVersion` varchar(20) NULL COMMENT 'android version',
  `forcedUpgrade` int(0) NULL COMMENT 'forcedUpgrade 0:no 1:yes',
  `questionnaireUrl` varchar(500) NULL COMMENT 'questionnaire url',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

insert into core_appConfig (iosVersion, androidVersion, forcedUpgrade, questionnaireUrl) value (null, null, null ,null );

ALTER TABLE core_userfocusedevent ADD COLUMN focusedDate datetime default null;


drop view if exists vw_userfocusedevent;
CREATE VIEW vw_userfocusedevent AS
SELECT CONCAT(cf.user_id, cf.focused_event_id) ID, cf.user_id UserId, cf.focused_event_id EventID, ve.owner_NPO_id NpoID , ve.image_link_1 Image1, ve.uid Uid, ve.happen_date HappenDate,
       ve.subject Subject, ve.required_volunteer_number ReqVolunteerNumer, ve.current_volunteer_number CurrVolunteeNumber, ve.thumb_path ThumbPath,
       1 IsFocus, CASE WHEN cu.id IS NOT NULL THEN 1 ELSE 0 END IsRegister, ve.is_volunteer_event, ve.is_enterprise, ve.pub_date pubDate, cf.focusedDate
FROM core_userfocusedevent cf
         LEFT JOIN vw_event ve ON cf.focused_event_id = ve.id
         LEFT JOIN core_userregisteredevent cu ON cf.user_id = cu.user_id AND cf.focused_event_id = cu.registered_event_id
ORDER BY UserId, EventID;

drop view if exists vw_eventvisithistory;
CREATE VIEW vw_eventvisithistory AS
SELECT CONCAT(ev.user_id, ev.event_id) ID, ev.user_id UserId, ev.event_id EventID, ev.visit_time as visitTime, ve.owner_NPO_id NpoID , ve.image_link_1 Image1, ve.uid Uid, ve.happen_date HappenDate,
       ve.subject Subject, ve.required_volunteer_number ReqVolunteerNumer, ve.current_volunteer_number CurrVolunteeNumber, ve.thumb_path ThumbPath,
       CASE WHEN cu.id IS NOT NULL THEN 1 ELSE 0 END IsRegister, CASE WHEN cu2.id IS NOT NULL THEN 1 ELSE 0 END IsFocus, ve.is_volunteer_event, ve.is_enterprise, ve.close_date as closeDate
FROM core_eventvisithistory ev
         LEFT JOIN vw_event ve ON ev.event_id = ve.id
         LEFT JOIN core_userfocusedevent cu2 ON ev.user_id = cu2.user_id AND ev.event_id = cu2.focused_event_id
         LEFT JOIN core_userregisteredevent cu on ev.event_id = cu.registered_event_id and ev.user_id = cu.user_id
ORDER BY UserId, EventID;

update db_version set tableVersion = "1.0.0.6", tableDate = now() where id = 1;