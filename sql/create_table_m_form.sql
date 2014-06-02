delimiter $$

DROP TABLE IF EXISTS `pcaie`.`m_form`$$

CREATE TABLE `pcaie`.`m_form` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `contact_by` int(1) DEFAULT NULL,
  `maker` varchar(45) DEFAULT NULL,
  `model` varchar(45) DEFAULT NULL,
  `content` text,
  `upd_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flg` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8$$

