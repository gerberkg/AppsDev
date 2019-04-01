CREATE DATABASE  IF NOT EXISTS `recipe_generator` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `recipe_generator`;
-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: recipe_generator
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(75) DEFAULT NULL,
  `last_mod_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `category_name_UNIQUE` (`category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='		';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (4,'Beef','2019-03-11 00:00:00',1),(5,'Chicken','2019-03-04 11:56:07',1),(6,'Breakfast','2019-03-04 11:56:07',1),(7,'Test Category','2019-03-11 00:00:00',0),(8,'Test Category 2','2019-03-11 00:00:00',0),(17,'','2019-03-11 00:00:00',1),(18,'Wild Game','2019-04-01 00:00:00',1);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorites`
--

DROP TABLE IF EXISTS `favorites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `favorites` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `recipe_id` int(11) DEFAULT NULL,
  `last_mod_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='			';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorites`
--

LOCK TABLES `favorites` WRITE;
/*!40000 ALTER TABLE `favorites` DISABLE KEYS */;
/*!40000 ALTER TABLE `favorites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingredient`
--

DROP TABLE IF EXISTS `ingredient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ingredient` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ingredient_name` varchar(75) DEFAULT NULL,
  `last_mod_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredient`
--

LOCK TABLES `ingredient` WRITE;
/*!40000 ALTER TABLE `ingredient` DISABLE KEYS */;
INSERT INTO `ingredient` VALUES (1,'Beef Roast','2019-03-27 00:00:00',1),(2,'Chicken Breast','2019-03-27 00:00:00',1),(3,'Fresh Basil Test','2019-03-11 00:00:00',1),(9,'Fresh Basil','2019-03-13 00:00:00',0),(10,'Chicken Drummies','2019-03-27 00:00:00',1),(11,'Chicken Wings','2019-03-27 00:00:00',1),(12,'Venison Steak','2019-04-01 00:00:00',1),(13,'Hash Browns','2019-04-01 00:00:00',1),(14,'Cheddar Cheese','2019-04-01 00:00:00',1),(15,'Sour Cream','2019-04-01 00:00:00',1),(16,'Onion','2019-04-01 00:00:00',1),(17,'Garlic','2019-04-01 00:00:00',1),(18,'Cream of Chicken Soup','2019-04-01 00:00:00',1);
/*!40000 ALTER TABLE `ingredient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipe`
--

DROP TABLE IF EXISTS `recipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `recipe` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `recipe_name` varchar(75) DEFAULT NULL,
  `last_mod_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `active` tinyint(1) DEFAULT '1',
  `serving_size` varchar(45) DEFAULT NULL,
  `instructions` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipe`
--

LOCK TABLES `recipe` WRITE;
/*!40000 ALTER TABLE `recipe` DISABLE KEYS */;
INSERT INTO `recipe` VALUES (1,'Beef Stroganoff test','2019-03-27 00:00:00',1,'4 Servings','Testing...'),(2,'Chicken Parmesan','2019-03-27 00:00:00',1,'4 People','Bake at 350 F. for 2 hours. test'),(3,'Breakfast Burrito','2019-03-11 00:00:00',1,'2 Servings',NULL),(4,'Beef Tacos','2019-03-11 00:00:00',1,'4 servings',NULL),(5,'Chicken Tacos','2019-03-11 00:00:00',1,'2 Servings',NULL),(6,'Chicken Tacos','2019-03-11 00:00:00',1,'2 Servings',NULL),(7,'Fish Tacos','2019-03-11 00:00:00',1,'2 Servings',NULL),(8,'Fish Tacos Test','2019-03-11 00:00:00',1,'1 serving',NULL),(9,'Fish Tacos Test','2019-03-11 00:00:00',1,'1 serving',NULL),(10,'Test Recipe','2019-03-11 00:00:00',1,'1 serving',NULL),(11,'Chicken Wings','2019-03-27 00:00:00',0,'12 Wings','coat wings with rub and put on smoker for 2 hours.'),(12,'Chicken Wings','2019-03-27 00:00:00',0,'12 Wings','coat wings with rub and put on smoker for 2 hours.'),(13,'Venison Steaks','2019-04-01 00:00:00',0,'5 - 7 Steaks','Preheat the frying pan and saute onions in butter until starting to brown.\r\nAdd the venison steaks and cover.  Turn the steaks after about 7 minutes.'),(14,'Cheesy Potatoes','2019-04-01 00:00:00',1,'2 lbs','Mix all the ingredients together in a large bowl.  Place in a greased 9 * 13 pan or glass baking dish.  Bake at 350 F for 45 - 60 minutes or cheese is starting to brown on top.');
/*!40000 ALTER TABLE `recipe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipe_category`
--

DROP TABLE IF EXISTS `recipe_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `recipe_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `recipe_id` int(11) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipe_category`
--

LOCK TABLES `recipe_category` WRITE;
/*!40000 ALTER TABLE `recipe_category` DISABLE KEYS */;
INSERT INTO `recipe_category` VALUES (1,1,4),(2,2,5);
/*!40000 ALTER TABLE `recipe_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipe_ingredient`
--

DROP TABLE IF EXISTS `recipe_ingredient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `recipe_ingredient` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `recipe_id` int(11) DEFAULT NULL,
  `ingredient_id` int(11) DEFAULT NULL,
  `last_mod_date` datetime DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `ingredient_amount` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipe_ingredient`
--

LOCK TABLES `recipe_ingredient` WRITE;
/*!40000 ALTER TABLE `recipe_ingredient` DISABLE KEYS */;
INSERT INTO `recipe_ingredient` VALUES (1,1,1,NULL,NULL,'2 - 4 lbs'),(2,2,2,NULL,NULL,'1 lbs'),(3,1,4,NULL,NULL,NULL),(4,4,1,NULL,NULL,NULL),(5,11,2,NULL,NULL,NULL),(6,12,2,NULL,NULL,NULL),(7,13,12,NULL,NULL,'2 - 4 lbs'),(8,14,13,NULL,NULL,'2 lbs'),(9,14,14,NULL,NULL,'2- 3 cups'),(10,1,15,NULL,NULL,NULL),(11,1,16,NULL,NULL,NULL),(12,1,18,NULL,NULL,NULL),(13,1,15,NULL,NULL,NULL),(14,1,16,NULL,NULL,NULL),(15,1,18,NULL,NULL,NULL),(16,1,15,NULL,NULL,NULL),(17,1,16,NULL,NULL,NULL),(18,1,18,NULL,NULL,NULL),(19,1,15,NULL,NULL,NULL),(20,1,16,NULL,NULL,NULL),(21,1,18,NULL,NULL,NULL),(22,1,15,NULL,NULL,NULL),(23,1,16,NULL,NULL,NULL),(24,1,18,NULL,NULL,NULL),(25,14,15,NULL,NULL,'8 oz.'),(26,14,16,NULL,NULL,'1 medium finely chopped'),(27,14,18,NULL,NULL,'1 8oz Can');
/*!40000 ALTER TABLE `recipe_ingredient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(45) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `user_id` varchar(45) DEFAULT NULL,
  `email_address` varchar(75) DEFAULT NULL,
  `last_mod_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `active` tinyint(4) NOT NULL DEFAULT '1',
  `password` varchar(45) NOT NULL,
  `isAdmin` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Bangsberg','Andy','abangs','','2019-02-25 00:00:00',1,'password',0),(3,'Gerber','Kevin','gerberkg','gerberkg@gmail.com','2019-02-25 00:00:00',1,'password',0),(9,'Bangsberg','Andy','abangs2','','2019-02-25 00:00:00',1,'qqq',0),(12,'Gerber','Kevin','keving','gerberkg@gmail.com','2019-03-04 00:00:00',1,'pwd',1),(13,'Bylander','Rene','reneb','rene.bylander@witc.edu','2019-03-11 00:00:00',1,'password',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'recipe_generator'
--

--
-- Dumping routines for database 'recipe_generator'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-01 12:01:02
