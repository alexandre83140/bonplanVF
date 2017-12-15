CREATE TABLE `userBP` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `nameU` varchar(30) NOT NULL,
  PRIMARY KEY (`user_id`)
);

CREATE TABLE `bonplan` (
  `BP_id` int(11) NOT NULL AUTO_INCREMENT,
  `nomBP` varchar(45) NOT NULL,
  `descriBP` longtext NOT NULL,
  `lieuBP` varchar(45) NOT NULL,
  `prixBP` int(11) NOT NULL,
  `dateBP` DATE NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`BP_id`),
  KEY `user_id_fk` (`user_id`),
  CONSTRAINT `user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `userBP` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);