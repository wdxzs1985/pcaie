delimiter $$

DROP TABLE IF EXISTS `pcaie`.`d_estimate`$$

CREATE TABLE `pcaie`.`d_estimate` (
  `id` int(11) NOT NULL,
  `unit1_price` int(11) NOT NULL DEFAULT '0',
  `unit1_comment` varchar(100) DEFAULT NULL,
  `unit2_price` int(11) NOT NULL DEFAULT '0',
  `unit2_comment` varchar(100) DEFAULT NULL,
  `unit3_price` int(11) NOT NULL DEFAULT '0',
  `unit3_comment` varchar(100) DEFAULT NULL,
  `unit4_price` int(11) NOT NULL DEFAULT '0',
  `unit4_comment` varchar(100) DEFAULT NULL,
  `unit5_price` int(11) NOT NULL DEFAULT '0',
  `unit5_comment` varchar(100) DEFAULT NULL,
  `unit6_price` int(11) NOT NULL DEFAULT '0',
  `unit6_comment` varchar(100) DEFAULT NULL,
  `unit7_price` int(11) NOT NULL DEFAULT '0',
  `unit7_comment` varchar(100) DEFAULT NULL,
  `unit8_price` int(11) NOT NULL DEFAULT '0',
  `unit8_comment` varchar(100) DEFAULT NULL,
  `unit9_price` int(11) NOT NULL DEFAULT '0',
  `unit9_comment` varchar(100) DEFAULT NULL,
  `total` int(11) NOT NULL DEFAULT '0',
  `upd_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flg` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$