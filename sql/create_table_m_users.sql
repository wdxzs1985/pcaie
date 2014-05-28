delimiter $$

DROP TABLE IF EXISTS pcaie.`m_users`$$

CREATE TABLE pcaie.`m_users` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `nickname` varchar(45) NOT NULL,
    `email` varchar(100) NOT NULL,
    `password` varchar(32) NOT NULL,
    `upd_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `del_flg` tinyint(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`),
    UNIQUE KEY `email_UNIQUE` (`email`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8$$

