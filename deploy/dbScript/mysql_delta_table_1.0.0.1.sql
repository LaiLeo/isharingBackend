--
-- Table structure for table `db_version`
--

DROP TABLE IF EXISTS `db_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `db_version` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `tableVersion` varchar(255) NOT NULL,
  `dataVersion` varchar(255) NOT NULL,
  `tableDate` datetime NOT NULL,
  `dataDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

insert into db_version (tableVersion, dataVersion, tableDate, dataDate) value ('1.0.0.0', '1.0.0.0', now(), now());

--
-- Alter column is_valid for table `auth_user`
--
ALTER TABLE auth_user ADD COLUMN is_valid tinyint(1) default 1;


--
-- Create view vw_users
--
CREATE VIEW `vw_users` AS
select u.id, u.is_active, u.username, u.first_name, u.last_name, u.last_login, u.email, u.is_staff, u.date_joined, u.is_valid,
       ua.uid, ua.photo, ua.name, ua.phone, ua.security_id, ua.skills_description, ua.birthday,
       ua.guardian_name, ua.guardian_phone, ua.icon, ua.thumb_path, ua.is_public, ua.interest, ua.about_me,
       ua.score, ua.ranking, ua.event_hour, ua.event_num, ua.event_enterprise_hour, ua.event_general_hour,
       case when n.is_verified = 1 then '1' else '0' end as is_npo
from auth_user u
left join core_useraccount ua on u.id=ua.user_id
left join core_npo n on u.id=n.user_id;


--
-- Alter column is_valid for table `db_version`
--
update db_version set tableVersion = "1.0.0.1", tableDate = now() where id = 1;