delimiter $$

DROP TABLE IF EXISTS pcaie.`r_user_password_history`$$

CREATE TABLE pcaie.`r_user_password_history` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `user_id` int(11) NOT NULL,
    `password` varchar(32) NOT NULL,
    `token` varchar(32) NOT NULL,
    `expire` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
    `upd_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `del_flg` tinyint(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8$$

