--
-- Create view vw_users
--
drop view if exists vw_users;
CREATE VIEW `vw_users` AS
select u.id, u.is_active, u.username, u.first_name, u.last_name, u.last_login, u.email, u.is_staff, u.date_joined,
       ua.uid, ua.photo, ua.name, ua.phone, ua.security_id, ua.skills_description, ua.birthday,
       ua.guardian_name, ua.guardian_phone, ua.icon, ua.thumb_path, ua.is_public, ua.interest, ua.about_me,
       ua.score, ua.ranking, ua.event_hour, ua.event_num, ua.event_enterprise_hour, ua.event_general_hour,
       case when n.is_verified = 1 then '1' else '0' end as is_npo, n.id as npoId,
       (SELECT count(ut.id) FROM core_user_third ut where u.id=ut.user_id and ut.enterpriseSerialCode = 'fubon') AS is_fubon,
       (SELECT count(ut.id) FROM core_user_third ut where u.id=ut.user_id and ut.enterpriseSerialCode = 'twm') AS is_twm
from auth_user u
         left join core_useraccount ua on u.id=ua.user_id
         left join core_npo n on u.id=n.user_id;

drop view if exists vw_userfocusedevent;
CREATE VIEW vw_userfocusedevent AS
SELECT CONCAT(cf.user_id, cf.focused_event_id) ID, cf.user_id UserId, cf.focused_event_id EventID, ve.owner_NPO_id NpoID , ve.image_link_1 Image1, ve.uid Uid, ve.happen_date HappenDate,
ve.subject Subject, ve.required_volunteer_number ReqVolunteerNumer, ve.current_volunteer_number CurrVolunteeNumber, ve.thumb_path ThumbPath,
1 IsFocus, CASE WHEN cu.id IS NOT NULL THEN 1 ELSE 0 END IsRegister, ve.is_volunteer_event, ve.is_enterprise
FROM core_userfocusedevent cf
LEFT JOIN vw_event ve ON cf.focused_event_id = ve.id
LEFT JOIN core_userregisteredevent cu ON cf.user_id = cu.user_id AND cf.focused_event_id = cu.registered_event_id
ORDER BY UserId, EventID;

drop view if exists vw_eventvisithistory;
CREATE VIEW vw_eventvisithistory AS
SELECT CONCAT(ev.user_id, ev.event_id) ID, ev.user_id UserId, ev.event_id EventID, ev.visit_time as visitTime, ve.owner_NPO_id NpoID , ve.image_link_1 Image1, ve.uid Uid, ve.happen_date HappenDate,
ve.subject Subject, ve.required_volunteer_number ReqVolunteerNumer, ve.current_volunteer_number CurrVolunteeNumber, ve.thumb_path ThumbPath,
CASE WHEN cu.id IS NOT NULL THEN 1 ELSE 0 END IsRegister, CASE WHEN cu2.id IS NOT NULL THEN 1 ELSE 0 END IsFocus, ve.is_volunteer_event, ve.is_enterprise
FROM core_eventvisithistory ev
LEFT JOIN vw_event ve ON ev.event_id = ve.id
LEFT JOIN core_userfocusedevent cu2 ON ev.user_id = cu2.user_id AND ev.event_id = cu2.focused_event_id
LEFT JOIN core_userregisteredevent cu on ev.event_id = cu.registered_event_id
ORDER BY UserId, EventID;


CREATE INDEX ind_userId_eventId ON core_eventvisithistory(user_id,event_id);

--
-- Alter column newebpayPeriodUrl for table `core_donationnpo`
--
ALTER TABLE core_donationnpo ADD COLUMN newebpayPeriodUrl varchar(300);



drop view if exists vw_userSubscribedNpos;
CREATE VIEW vw_userSubscribedNpos AS
SELECT CONCAT(us.user_id, n.id) ID, us.user_id as UserId, n.id as npoId, n.name as npoName, n.uid as npoUid, n.npo_icon as npoIcon,
n.rating_user_num as ratingUserNum, n.total_rating_score as totalRatingScore, n.subscribed_user_num as subscribedUserNum,
n.joined_user_num as joinedUserNum, n.event_num as eventNum, n.is_verified as isVerified, n.adm_viewed as admViewed, n.administrator_id as admId
FROM core_usersubscribednpo us
LEFT JOIN core_npo n ON us.subscribed_NPO_id = n.id;


drop view if exists vw_userregisteredevent;
CREATE VIEW vw_userregisteredevent AS
SELECT CONCAT(cu.user_id, cu.registered_event_id) ID, cu.user_id UserId, cu.registered_event_id EventID, ve.owner_NPO_id NpoID , ve.image_link_1 Image1, ve.uid Uid, ve.happen_date HappenDate,
ve.subject Subject, ve.required_volunteer_number ReqVolunteerNumer, ve.current_volunteer_number CurrVolunteeNumber, ve.thumb_path ThumbPath,
1 IsRegister, CASE WHEN cu2.id IS NOT NULL THEN 1 ELSE 0 END IsFocus, ve.is_volunteer_event, ve.is_enterprise, cu.isJoined, cu.score,
cu.name as registeredName, cu.phone as registeredPhone, cu.email as registeredEmail, cu.skills as registeredSkills, cu.birthday as registeredBirthday,
cu.guardian_name as registeredGuardianName, cu.guardian_phone as guardianPhone, cu.security_id as securityId, cu.note,
cu.employee_serial_number as employeeSerialNumber,cu.enterprise_serial_number as enterpriseSerialNumberm, cu.isLeaved, cu.join_time as joinTime, cu.leave_time as leaveTime,
cu.register_date as registerDate, cut.enterpriseSerialNumber as thirdEnterpriseSerialNumber, u.score as userScore
FROM core_userregisteredevent cu
LEFT JOIN vw_event ve ON cu.registered_event_id = ve.id
LEFT JOIN core_userfocusedevent cu2 ON cu.user_id = cu2.user_id AND cu.registered_event_id = cu2.focused_event_id
LEFT JOIN core_user_third cut on cu.user_id = cut.user_id
LEFT JOIN core_useraccount u on cu.user_id = u.id
ORDER BY UserId, EventID;


ALTER TABLE core_banner ADD COLUMN displaySort tinyint(1) default null;

ALTER TABLE core_event ADD COLUMN promote tinyint(1) default 0;
ALTER TABLE core_npo ADD COLUMN promote tinyint(1) default 0;

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
    n.is_enterprise, e.promote
from core_event e
         left join core_npo n on e.owner_npo_id = n.id
order by e.is_Urgent desc;

ALTER TABLE auth_user ADD COLUMN resource varchar(50) default "isharing" comment 'Identify user resources';


DROP view IF EXISTS `vw_eventvisithistory`;
CREATE VIEW `vw_eventvisithistory` AS
    select concat(`ev`.`user_id`,`ev`.`event_id`) AS `ID`,`ev`.`user_id` AS `UserId`,`ev`.`event_id` AS `EventID`
         ,`ev`.`visit_time` AS `visitTime`,`ve`.`owner_NPO_id` AS `NpoID`,`ve`.`image_link_1` AS `Image1`
         ,`ve`.`uid` AS `Uid`,`ve`.`happen_date` AS `HappenDate`,`ve`.`subject` AS `Subject`
         ,`ve`.`required_volunteer_number` AS `ReqVolunteerNumer`,`ve`.`current_volunteer_number` AS `CurrVolunteeNumber`
         ,`ve`.`thumb_path` AS `ThumbPath`,(case when (`cu`.`id` is not null) then 1 else 0 end) AS `IsRegister`
         ,(case when (`cu2`.`id` is not null) then 1 else 0 end) AS `IsFocus`,`ve`.`is_volunteer_event` AS `is_volunteer_event`
         ,`ve`.`is_enterprise` AS `is_enterprise`, `ve`.`close_date` as closeDate
    from (((`core_eventvisithistory` `ev` left join `vw_event` `ve` on((`ev`.`event_id` = `ve`.`id`)))
        left join `core_userfocusedevent` `cu2` on(((`ev`.`user_id` = `cu2`.`user_id`) and (`ev`.`event_id` = `cu2`.`focused_event_id`))))
        left join `core_userregisteredevent` `cu` on((`ev`.`event_id` = `cu`.`registered_event_id`))) order by `ev`.`user_id`,`ev`.`event_id`;


ALTER TABLE core_donationnpo ADD COLUMN displaySort int(10) not null default 0;


DROP view IF EXISTS `vw_npoPromote`;
CREATE VIEW `vw_npoPromote` AS
select n.id, n.name, n.uid, n.promote, e.id as eventId, e.subject as eventSubject, e.uid as eventUid, e.happen_date as eventHappenDate, e.close_date as eventCloseDate, e.required_volunteer_number as eventRequiredVolunteerNumber, e.current_volunteer_number as eventCurrentVolunteerNumber
from core_npo n
         left join core_event e on n.id = e.owner_NPO_id
where e.id is not null and e.required_volunteer_number > current_volunteer_number and DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s') <= DATE_FORMAT(e.close_date, '%Y-%m-%d %H:%i:%s')
order by n.promote desc, e.happen_date DESC
limit 10;

ALTER TABLE auth_user DROP COLUMN is_valid;

DROP view IF EXISTS `vw_eventCooperationNpos`;
CREATE VIEW `vw_eventCooperationNpos` AS
select ecn.id, ecn.event_id, ecn.npo_id, n.name as npoName
from core_event_cooperation_NPO ecn
left join core_npo n on ecn.npo_id = n.id;

drop view if exists vw_users;
CREATE VIEW `vw_users` AS
select u.id, u.is_active, u.username, u.first_name, u.last_name, u.last_login, u.email, u.is_staff, u.date_joined,
       ua.uid, ua.photo, ua.name, ua.phone, ua.security_id, ua.skills_description, ua.birthday,
       ua.guardian_name, ua.guardian_phone, ua.icon, ua.thumb_path, ua.is_public, ua.interest, ua.about_me,
       ua.score, ua.ranking, ua.event_hour, ua.event_num, ua.event_enterprise_hour, ua.event_general_hour,
       case when n.is_verified = 1 then '1' else '0' end as is_npo, n.id as npoId,
       (SELECT count(ut.id) FROM core_user_third ut where u.id=ut.user_id and ut.enterpriseSerialCode = 'fubon') AS is_fubon,
       (SELECT count(ut.id) FROM core_user_third ut where u.id=ut.user_id and ut.enterpriseSerialCode = 'twmEnterprise') AS is_twm
from auth_user u
         left join core_useraccount ua on u.id=ua.user_id
         left join core_npo n on u.id=n.user_id;

update db_version set tableVersion = "1.0.0.5", tableDate = now() where id = 1;