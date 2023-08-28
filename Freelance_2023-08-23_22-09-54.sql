# ************************************************************
# Antares - SQL Client
# Version 0.7.15
# 
# https://antares-sql.app/
# https://github.com/antares-sql/antares
# 
# Host: localhost ((Ubuntu) 8.0.33)
# Database: Freelance
# Generation time: 2023-08-23T22:09:57+01:00
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table client
# ------------------------------------------------------------

DROP TABLE IF EXISTS `client`;

CREATE TABLE `client` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `primary_user` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Client_fk0` (`primary_user`),
  CONSTRAINT `Client_fk0` FOREIGN KEY (`primary_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;

INSERT INTO `client` (`id`, `name`, `primary_user`) VALUES
	(1, "ABC Corporation", 1),
	(2, "XYZ Corporation", 2),
	(4, "test-corp", 2);

/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;



# Dump of table shift
# ------------------------------------------------------------

DROP TABLE IF EXISTS `shift`;

CREATE TABLE `shift` (
  `id` int NOT NULL AUTO_INCREMENT,
  `client_id` int NOT NULL,
  `shift_admin_id` int DEFAULT NULL,
  `worker_id` int DEFAULT NULL,
  `start` datetime NOT NULL,
  `end` datetime NOT NULL,
  `rate` decimal(10,2) NOT NULL,
  `total` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Shift_fk0` (`client_id`),
  CONSTRAINT `Shift_fk0` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `shift` WRITE;
/*!40000 ALTER TABLE `shift` DISABLE KEYS */;

INSERT INTO `shift` (`id`, `client_id`, `shift_admin_id`, `worker_id`, `start`, `end`, `rate`, `total`) VALUES
	(1, 1, 1, NULL, "2023-07-13 09:00:00", "2023-07-13 17:00:00", 16.5, 0),
	(2, 2, 1, NULL, "2023-07-14 13:00:00", "2023-07-14 21:00:00", 13.5, 0),
	(3, 1, 2, 3, "2023-07-15 13:00:00", "2023-07-16 00:00:00", 14.5, 0),
	(4, 1, 2, 3, "2023-07-15 13:00:00", "2023-07-16 00:00:00", 14, 0),
	(5, 1, 2, 2, "2023-07-15 09:00:00", "2023-07-15 17:00:00", 11, 0),
	(7, 1, 2, 2, "2023-07-15 09:00:00", "2023-07-15 17:00:00", 11, 0),
	(8, 1, 2, 2, "2023-07-15 09:00:00", "2023-07-15 17:00:00", 11, 0),
	(9, 1, 2, 2, "2023-08-01 00:00:00", "2023-08-01 08:00:00", 12, 0);

/*!40000 ALTER TABLE `shift` ENABLE KEYS */;
UNLOCK TABLES;



# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `verified` tinyint(1) NOT NULL DEFAULT '0',
  `type` int NOT NULL,
  `organisation` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `User_fk0` (`type`),
  CONSTRAINT `User_fk0` FOREIGN KEY (`type`) REFERENCES `user_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`id`, `first_name`, `last_name`, `email`, `password`, `verified`, `type`, `organisation`) VALUES
	(1, "John", "Doe", "john.doe@example.com", "password123", 1, 1, 0),
	(2, "Jane", "Smith", "jane.smith@example.com", "test123", 0, 2, 0),
	(3, "Mike", "Johnson", "mike.johnson@example.com", "securepwd", 1, 1, 0),
	(4, "Alex", "Mcc", "ally-bally@example.com", "Carmex23", 1, 1, NULL),
	(57, "LOL", "Scoular", "candyscouly@gmail.com", "Carmex123", 0, 3, NULL),
	(58, "UpdatedFirstName", "UpdatedLastName", "updated.email@example.com", "Carmex123", 1, 2, NULL),
	(59, "Candace", "Scoular", "candyscouly@gmail.com", "Carmex123", 0, 3, NULL),
	(60, "Candace", "Scoular", "candyscouly@gmail.com", "Carmex123", 0, 3, NULL),
	(61, "Candace", "Scoular", "candyscouly@gmail.com", "Carmex123", 0, 3, NULL),
	(62, "A", "B", "C", "D", 0, 1, 1);

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;



# Dump of table user_type
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_type`;

CREATE TABLE `user_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `user_type` WRITE;
/*!40000 ALTER TABLE `user_type` DISABLE KEYS */;

INSERT INTO `user_type` (`id`, `description`) VALUES
	(1, "Admin"),
	(2, "Regular User"),
	(3, "Worker");

/*!40000 ALTER TABLE `user_type` ENABLE KEYS */;
UNLOCK TABLES;



# Dump of views
# ------------------------------------------------------------

# Creating temporary tables to overcome VIEW dependency errors


/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

# Dump completed on 2023-08-23T22:09:57+01:00
