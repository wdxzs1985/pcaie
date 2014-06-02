delimiter $$

DROP TABLE IF EXISTS `pcaie`.`m_customer`$$

CREATE TABLE `pcaie`.`m_customer` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(45) NOT NULL,
    `kana` varchar(45) DEFAULT NULL,
    `employment` varchar(45) DEFAULT NULL,
    `department` varchar(45) DEFAULT NULL,
    `email` varchar(100) DEFAULT NULL,
    `tel` varchar(20) DEFAULT NULL,
    `upd_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `del_flg` int(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
)  ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8$$

