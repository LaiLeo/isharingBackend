--
-- Table structure for table `core_user_third`
--

DROP TABLE IF EXISTS `core_user_third`;


CREATE TABLE `core_user_third` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(20) NOT NULL,
  `enterpriseSerialCode` varchar(255) NOT NULL,
  `enterpriseSerialNumber` varchar(255) NOT NULL,
  `enterpriseSerialEmail` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


update db_version set tableVersion = "1.0.0.2", tableDate = now() where id = 1;