--(3) MySQL schema used for the log data

--ps..: The application will create the schemas automatically

CREATE TABLE `access_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date_time` datetime,
  `ip_address` varchar(255),
  `request` varchar(255),
  `status` int(11),
  `user_agent` varchar(255),
  PRIMARY KEY (`id`)
);

CREATE TABLE `blocked_ip_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip_address` varchar(255),
  `comment` varchar(255),
  PRIMARY KEY (`id`)
) ;
