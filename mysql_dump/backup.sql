-- MySQL dump 10.13  Distrib 5.7.28, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: bbs_lite
-- ------------------------------------------------------
-- Server version	5.7.28-0ubuntu0.18.04.4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `bbs_lite`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `bbs_lite` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `bbs_lite`;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `description` text,
  `creator` bigint(20) DEFAULT NULL,
  `tag` varchar(256) DEFAULT NULL,
  `top` tinyint(4) DEFAULT '0',
  `topic` tinyint(4) DEFAULT NULL,
  `category` varchar(10) DEFAULT NULL,
  `user_avatar_url` varchar(100) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `follow_count` bigint(20) DEFAULT '0',
  `view_count` bigint(20) DEFAULT '0',
  `like_count` bigint(20) DEFAULT '0',
  `comment_count` bigint(20) DEFAULT '0',
  `status` tinyint(4) DEFAULT '1',
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (1,'123','1232131231123123qweqwe123123		23',1,'1231',0,1,NULL,'https://avatars1.githubusercontent.com/u/43701948?v=4','bestsort',0,12,1,12,1,1575880247614,1575880247614);
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment_kid`
--

DROP TABLE IF EXISTS `comment_kid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment_kid` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment_by_id` bigint(20) DEFAULT NULL,
  `comment_to_user_id` bigint(20) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  `gmt_create` bigint(20) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_kid`
--

LOCK TABLES `comment_kid` WRITE;
/*!40000 ALTER TABLE `comment_kid` DISABLE KEYS */;
INSERT INTO `comment_kid` VALUES (1,3,3,1,1576828125756,1576828125756,'asds自行车啊'),(2,5,3,2,1576828146137,1576828146137,'2141阿斯顿'),(3,5,3,1,1576828150426,1576828150426,'2141阿斯顿现在从'),(4,5,3,2,1576828195156,1576828195156,'现在擦拭的'),(5,6,5,3,1576828221583,1576828221583,'阿斯顿'),(6,2,7,5,1576828254375,1576828254375,'现在擦拭的');
/*!40000 ALTER TABLE `comment_kid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment_parent`
--

DROP TABLE IF EXISTS `comment_parent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment_parent` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment_by_id` bigint(20) DEFAULT NULL,
  `article_id` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  `gmt_create` bigint(20) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `like_count` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_parent`
--

LOCK TABLES `comment_parent` WRITE;
/*!40000 ALTER TABLE `comment_parent` DISABLE KEYS */;
INSERT INTO `comment_parent` VALUES (1,3,1,1576828119345,1576828119345,'123124123',0),(2,3,1,1576828128801,1576828128801,'123124123阿斯顿',0),(3,5,1,1576828199528,1576828199528,'现在从',0),(4,6,1,1576828224453,1576828224453,'这些擦拭',0),(5,7,1,1576828239385,1576828239385,'在潇洒的',0),(6,2,1,1576828256776,1576828256776,'爱上打算的',0);
/*!40000 ALTER TABLE `comment_parent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fans`
--

DROP TABLE IF EXISTS `fans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fans` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fans_by_id` bigint(20) DEFAULT NULL,
  `fans_to_id` bigint(20) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '1',
  `gmt_created` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fans`
--

LOCK TABLES `fans` WRITE;
/*!40000 ALTER TABLE `fans` DISABLE KEYS */;
/*!40000 ALTER TABLE `fans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flyway_schema_history`
--

DROP TABLE IF EXISTS `flyway_schema_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flyway_schema_history`
--

LOCK TABLES `flyway_schema_history` WRITE;
/*!40000 ALTER TABLE `flyway_schema_history` DISABLE KEYS */;
INSERT INTO `flyway_schema_history` VALUES (1,'1','Create user table','SQL','V1__Create_user_table.sql',1782121467,'bestsort','2019-12-05 05:23:23',404,1),(2,'2','Create question table','SQL','V2__Create_question_table.sql',-1071101994,'bestsort','2019-12-05 05:23:24',356,1),(3,'3','Create comment parent table','SQL','V3__Create_comment_parent_table.sql',-1990481420,'bestsort','2019-12-05 05:23:25',677,1),(4,'4','Create topic table','SQL','V4__Create_topic_table.sql',1140924444,'bestsort','2019-12-05 05:23:25',427,1),(5,'5','Create thumb up table','SQL','V5__Create_thumb_up_table.sql',2084120044,'bestsort','2019-12-05 05:23:26',389,1),(6,'6','Create follow table','SQL','V6__Create_follow_table.sql',2124364077,'bestsort','2019-12-05 05:23:26',404,1),(7,'7','Create fans table','SQL','V7__Create_fans_table.sql',1031802539,'bestsort','2019-12-05 05:23:26',352,1),(8,'8','Create message table','SQL','V8__Create_message_table.sql',704977853,'bestsort','2019-12-05 05:23:27',400,1),(9,'9','Create user buffer table','SQL','V9__Create_user_buffer_table.sql',712067459,'bestsort','2019-12-05 05:23:27',403,1),(10,'10','Create comment kid table','SQL','V10__Create_comment_kid_table.sql',1361701963,'bestsort','2019-12-05 05:23:28',403,1),(11,'11','Update some table','SQL','V11__Update_some_table.sql',1751674195,'bestsort','2019-12-05 05:23:30',1804,1),(12,'12','Create oauth user table','SQL','V12__Create_oauth_user_table.sql',-130985669,'bestsort','2019-12-05 05:23:32',1747,1);
/*!40000 ALTER TABLE `flyway_schema_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `follow`
--

DROP TABLE IF EXISTS `follow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `follow` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `follow_by` bigint(20) DEFAULT NULL,
  `follow_to` bigint(20) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follow`
--

LOCK TABLES `follow` WRITE;
/*!40000 ALTER TABLE `follow` DISABLE KEYS */;
INSERT INTO `follow` VALUES (1,3,1,3,1576828116005,1576828116005,1),(2,2,1,3,1576828264231,1576828264231,1);
/*!40000 ALTER TABLE `follow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` tinyint(4) DEFAULT NULL COMMENT '类型(被收藏/被关注/被回复等)',
  `send_by` bigint(20) DEFAULT '0',
  `send_to` bigint(20) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '1' COMMENT '是否可见(为0表示已删除)',
  `is_read` tinyint(4) DEFAULT '0',
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth_user`
--

DROP TABLE IF EXISTS `oauth_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(50) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `nickname` varchar(20) DEFAULT NULL,
  `avatar` varchar(100) DEFAULT NULL,
  `blog` varchar(50) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  `source` varchar(20) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `gmt_created` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_user`
--

LOCK TABLES `oauth_user` WRITE;
/*!40000 ALTER TABLE `oauth_user` DISABLE KEYS */;
INSERT INTO `oauth_user` VALUES (1,'43701948',1,'bestsort','bestsort','https://avatars1.githubusercontent.com/u/43701948?v=4','bestsort.cn','你指尖跃动的电光 是我此生不变的信仰','GITHUB','bestsort@qq.com',1575880223918,1575880223918);
/*!40000 ALTER TABLE `oauth_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thumb_up`
--

DROP TABLE IF EXISTS `thumb_up`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `thumb_up` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `thumb_up_to` bigint(20) DEFAULT NULL,
  `thumb_up_by` bigint(20) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '1',
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thumb_up`
--

LOCK TABLES `thumb_up` WRITE;
/*!40000 ALTER TABLE `thumb_up` DISABLE KEYS */;
INSERT INTO `thumb_up` VALUES (1,1,2,3,1,1576828263766,1576828263766);
/*!40000 ALTER TABLE `thumb_up` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topic`
--

DROP TABLE IF EXISTS `topic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `topic` (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) DEFAULT NULL,
  `avatar_url` varchar(50) DEFAULT NULL,
  `follow_count` bigint(20) DEFAULT '0',
  `article_count` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic`
--

LOCK TABLES `topic` WRITE;
/*!40000 ALTER TABLE `topic` DISABLE KEYS */;
INSERT INTO `topic` VALUES (1,'默认',NULL,0,1),(2,'c++','https://alicdn.bestsort.cn/icon/cplusplus.png',0,0),(3,'java','https://alicdn.bestsort.cn/icon/java.png',0,0),(4,'mybatis','https://alicdn.bestsort.cn/icon/mybatis.png',0,0),(5,'redis','https://alicdn.bestsort.cn/icon/redis.png',0,0),(6,'mysql','https://alicdn.bestsort.cn/icon/mysql.png',0,0),(7,'氢论坛','https://alicdn.bestsort.cn/icon/bbs.png',0,0),(8,'python','https://alicdn.bestsort.cn/icon/python.png',0,0),(9,'springboot','https://alicdn.bestsort.cn/icon/springboot.png',0,0);
/*!40000 ALTER TABLE `topic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(20) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `token` varchar(36) DEFAULT NULL,
  `bio` varchar(256) DEFAULT NULL,
  `fans_count` bigint(20) DEFAULT '0',
  `avatar_url` varchar(100) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `html_url` varchar(50) DEFAULT NULL,
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_id` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,NULL,'bestsort@qq.com','bestsort',NULL,'你指尖跃动的电光 是我此生不变的信仰',0,'https://avatars1.githubusercontent.com/u/43701948?v=4',NULL,'bestsort.cn',1575880223918,1575880223918),(2,'123456','bestsort@qq.com','1',NULL,'bestsort@qq.com',0,'https://avatars1.githubusercontent.com/u/43701948?v=4','-8919845115964556630',NULL,NULL,NULL),(3,'1','bestsort@qq.com','2',NULL,'bestsort@qq.com',0,'https://avatars1.githubusercontent.com/u/43701948?v=4','-8919845115964556630',NULL,NULL,NULL),(4,'12','bestsort@qq.com','3',NULL,'bestsort@qq.com',0,'https://avatars1.githubusercontent.com/u/43701948?v=4','-8919845115964556630',NULL,NULL,NULL),(5,'123','bestsort@qq.com','4',NULL,'bestsort@qq.com',0,'https://avatars1.githubusercontent.com/u/43701948?v=4','-8919845115964556630',NULL,NULL,NULL),(6,'1234','bestsort@qq.com','5',NULL,'bestsort@qq.com',0,'https://avatars1.githubusercontent.com/u/43701948?v=4','-8919845115964556630',NULL,NULL,NULL),(7,'12345','bestsort@qq.com','6',NULL,'bestsort@qq.com',0,'https://avatars1.githubusercontent.com/u/43701948?v=4','-8919845115964556630',NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_buffer`
--

DROP TABLE IF EXISTS `user_buffer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_buffer` (
  `account_id` varchar(20) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `token` varchar(36) DEFAULT '',
  `password` varchar(29) DEFAULT NULL,
  UNIQUE KEY `account_id` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_buffer`
--

LOCK TABLES `user_buffer` WRITE;
/*!40000 ALTER TABLE `user_buffer` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_buffer` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-20 15:54:38
