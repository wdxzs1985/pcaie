delimiter $$

DROP TABLE IF EXISTS `pcaie`.`m_product`$$

CREATE TABLE `pcaie`.`m_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `content` text,
  `upd_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flg` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
)  ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8$$

