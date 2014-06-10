delimiter $$

DROP TABLE IF EXISTS `pcaie`.`m_stock`$$

CREATE TABLE `pcaie`.`m_stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `price` int(10) unsigned NOT NULL DEFAULT '0',
  `stock` int(10) unsigned NOT NULL DEFAULT '0',
  `safe_stock` int(10) unsigned NOT NULL DEFAULT '0',
  `notification_email` varchar(100) DEFAULT NULL,
  `upd_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flg` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
)  ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8$$