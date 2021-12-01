
DROP TABLE IF EXISTS `core_scoreRecords`;
CREATE TABLE `core_scoreRecords` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `eventName` varchar(300) NULL COMMENT 'event subject',
  `eventId` int(11) NULL COMMENT 'event id',
  `userId` int(11) NOT NULL COMMENT 'user id',
  `score` int(11) NOT NULL COMMENT 'score',
  `comment` varchar(100) NULL COMMENT 'comment',
  `addDate` datetime NOT NULL COMMENT 'insert time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

drop view if exists vw_dumpRanking;
CREATE VIEW vw_dumpRanking AS
select u.user_id as id, u.email, u.ranking, u.name, u.phone, GROUP_CONCAT(e.subject separator ';') as eventNames
from core_useraccount u
left join core_userregisteredevent ur on u.user_id=ur.user_id
left join core_event e on ur.registered_event_id=e.id
group by u.user_id
order by u.ranking asc;

drop view if exists vw_dumpFubonEvent;
CREATE VIEW vw_dumpFubonEvent AS
select CONCAT(ur.user_id, e.id) id, e.id as eventId, ua.email as userName, ur.security_id as securityId, ua.name, e.uid as eventUid,
       e.subject as eventSubject, ur.note, CONCAT(e.happen_date, "~", e.close_date) as eventDate, e.event_hour as eventHour
from core_userregisteredevent ur
         left join core_user_third ut on ur.user_id=ut.user_id
         left join core_event e on ur.registered_event_id=e.id
         left join core_useraccount ua on ur.user_id=ua.user_id
where enterpriseSerialCode = 'fubon'
order by ur.registered_event_id desc;

drop view if exists vw_dumpTwmEvent;
CREATE VIEW vw_dumpTwmEvent AS
select CONCAT(ur.user_id, e.id) id, e.id as eventId, au.username as userName, ur.security_id as securityId, ua.name, e.uid as eventUid,
       e.subject as eventSubject, ur.note, CONCAT(e.happen_date, "~", e.close_date) as eventDate, e.event_hour as eventHour,
       te.enterpriseSerialName, te.enterpriseSerialNumber, te.enterpriseSerialEmail, tei.enterpriseSerialId, tei.enterpriseSerialPhone,
       tei.enterpriseSerialDepartment, tei.enterpriseSerialType, tei.enterpriseSerialGroup
from core_userregisteredevent ur
         left join core_user_third ut on ur.user_id=ut.user_id
         left join core_event e on ur.registered_event_id=e.id
         left join core_useraccount ua on ur.user_id=ua.user_id
         left join auth_user au on au.id=ua.user_id
         left join third_twmEnterprise te on ut.enterpriseSerialNumber = te.enterpriseSerialNumber
         left join third_twmEnterpriseInfo tei on te.enterpriseSerialNumber = tei.enterpriseSerialNumber
where ut.enterpriseSerialCode = 'twmEnterprise' and te.enterpriseSerialNumber is not null
order by ur.registered_event_id desc;

drop view if exists vw_userfocusedevent;
CREATE VIEW vw_userfocusedevent AS
SELECT CONCAT(cf.user_id, cf.focused_event_id) ID, cf.user_id UserId, cf.focused_event_id EventID, ve.owner_NPO_id NpoID , ve.image_link_1 Image1, ve.uid Uid, ve.happen_date HappenDate,
       ve.subject Subject, ve.required_volunteer_number ReqVolunteerNumer, ve.current_volunteer_number CurrVolunteeNumber, ve.thumb_path ThumbPath,
       1 IsFocus, CASE WHEN cu.id IS NOT NULL THEN 1 ELSE 0 END IsRegister, ve.is_volunteer_event, ve.is_enterprise, ve.pub_date pubDate, cf.focusedDate, ve.close_date as closedDate
FROM core_userfocusedevent cf
         LEFT JOIN vw_event ve ON cf.focused_event_id = ve.id
         LEFT JOIN core_userregisteredevent cu ON cf.user_id = cu.user_id AND cf.focused_event_id = cu.registered_event_id
ORDER BY UserId, EventID;

update db_version set tableVersion = "1.0.0.7", tableDate = now() where id = 1;