-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ticketdatabase
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `discount`
--

DROP TABLE IF EXISTS `discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `discount` (
  `discountSequence` int NOT NULL AUTO_INCREMENT COMMENT '折扣序号，自增主键，1代表儿童，2代表学生',
  `value` float NOT NULL COMMENT '折扣值，值介于0到1之间',
  `type` varchar(20) NOT NULL COMMENT '折扣类型，取学生或儿童',
  PRIMARY KEY (`discountSequence`),
  CONSTRAINT `CHK_Type` CHECK ((`type` in (_utf8mb4'学生',_utf8mb4'儿童'))),
  CONSTRAINT `CHK_Value` CHECK (((`value` > 0) and (`value` <= 1)))
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discount`
--

LOCK TABLES `discount` WRITE;
INSERT INTO `discount` VALUES (1,1,'儿童'),(2,1,'学生');
UNLOCK TABLES;

--
-- Table structure for table `myaccount`
--

DROP TABLE IF EXISTS `myaccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `myaccount` (
  `accountSequence` int NOT NULL AUTO_INCREMENT COMMENT '账户序号，自增主键',
  `accountId` varchar(20) NOT NULL COMMENT '账户ID',
  `accountName` varchar(20) DEFAULT NULL COMMENT '用户名',
  `password` varchar(20) NOT NULL COMMENT '登录密码',
  `accountType` tinyint NOT NULL COMMENT '账户类型，标识账户属于会员还是管理员，1代表管理员，2代表会员账户',
  `paymentMethod` varchar(20) DEFAULT NULL COMMENT '支付方式，只能取支付宝或者微信支付，当账户类型为管理员时，支付方式设为null',
  `registerTime` datetime(6) NOT NULL COMMENT '注册时间',
  `personSequence_FK` int DEFAULT NULL COMMENT '外键，标识该账户属于哪一个人',
  PRIMARY KEY (`accountSequence`),
  UNIQUE KEY `UNIQ_AccountId` (`accountId`),
  UNIQUE KEY `UNIQ_AccountName` (`accountName`),
  KEY `FK_person` (`personSequence_FK`),
  CONSTRAINT `FK_person` FOREIGN KEY (`personSequence_FK`) REFERENCES `person` (`personSequence`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `CHK_AccountType` CHECK ((`accountType` in (1,2))),
  CONSTRAINT `CHK_PaymentMethod` CHECK ((`paymentMethod` in (_utf8mb4'支付宝',_utf8mb4'微信支付',NULL)))
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `myaccount`
--

LOCK TABLES `myaccount` WRITE;
INSERT INTO `myaccount` VALUES (1,'0000001','管一','0000001',1,'微信','2023-12-27 00:00:00.000000',1),(2,'100001','张三','zhangsan',2,'支付宝','2023-12-27 00:00:00.000000',2),(3,'0000002','管二','0000001',1,NULL,'2023-12-27 00:00:00.000000',3),(4,'100002','李四','lisi',2,'微信','2023-12-27 00:00:00.000000',4),(5,'f14574a565d547fc454f','理一','0000201',1,NULL,'2023-12-28 00:00:00.000000',5),(6,'96f701847aa5f15b8932','管三','123456',1,NULL,'2023-12-28 00:00:00.000000',1),(7,'5df9902ad825024c497a','王五','123456',2,'微信支付','2023-12-28 00:00:00.000000',7),(8,'f44d0cc73f8ffb2a953b','王六','123456',2,'支付宝','2023-12-28 00:00:00.000000',2);
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person` (
  `personSequence` int NOT NULL AUTO_INCREMENT COMMENT '乘客序号，自增主键',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `gender` tinyint DEFAULT NULL COMMENT '性别，取1表示男，取2表示女',
  `age` tinyint DEFAULT NULL COMMENT '年龄',
  `identificationId` varchar(18) NOT NULL COMMENT '身份证号',
  `contactInfo` varchar(20) DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`personSequence`),
  UNIQUE KEY `UNIQ_IdentificationId` (`identificationId`),
  CONSTRAINT `CHK_Age` CHECK (((`age` > 0) and (`age` < 100))),
  CONSTRAINT `CHK_Gender` CHECK ((`gender` in (1,2)))
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
INSERT INTO `person` VALUES (1,'王红',1,28,'987654321098765432','19254678956'),(2,'李晓',2,21,'777777777777777778','14325436546'),(3,'上官静',2,22,'777777777777777775','15467834560'),(4,'程橙',1,26,'966655559999999998','14432343546'),(5,'白华',1,30,'988456235654896548','18823142136'),(7,'徐白卿',2,28,'655236477896541235','18812907890');
UNLOCK TABLES;

--
-- Table structure for table `station`
--

DROP TABLE IF EXISTS `station`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `station` (
  `stationSequence` int NOT NULL AUTO_INCREMENT COMMENT '站点序号，自增主键',
  `stationName` varchar(20) NOT NULL COMMENT '站点名',
  PRIMARY KEY (`stationSequence`),
  UNIQUE KEY `UNIQ_StationName` (`stationName`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `station`
--

LOCK TABLES `station` WRITE;
INSERT INTO `station` VALUES (2,'上海站'),(1,'北京站'),(3,'南京站'),(12,'南昌站'),(4,'广州站'),(8,'成都站'),(6,'武汉站'),(11,'济南站'),(5,'深圳站'),(14,'石家庄站'),(9,'西安站'),(7,'重庆站'),(10,'长沙站'),(13,'青岛站');
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket` (
  `ticketSequence` int NOT NULL AUTO_INCREMENT COMMENT '车票的序号，自增主键',
  `ticketId` varchar(20) NOT NULL COMMENT '票的ID号',
  `wagonNum` tinyint NOT NULL COMMENT '车厢号，总共十个车厢',
  `seatNum` tinyint NOT NULL COMMENT '座位号，每个车厢20个座位',
  `orderTime` datetime(6) NOT NULL COMMENT '买票时间',
  `prize` float NOT NULL COMMENT '该票的基础售价',
  `accountSequence_FK` int DEFAULT NULL COMMENT '外键，连接到账户的序号，标识该车票被哪个账号买走',
  `trainServiceSequence_FK` int NOT NULL COMMENT '外键，连接到车次的序号，标识该车票代表哪一趟车次',
  PRIMARY KEY (`ticketSequence`),
  UNIQUE KEY `UNIQ_TicketId` (`ticketId`),
  KEY `FK_AccountSequence` (`accountSequence_FK`),
  KEY `FK_TrainServiceSequence` (`trainServiceSequence_FK`),
  CONSTRAINT `FK_AccountSequence` FOREIGN KEY (`accountSequence_FK`) REFERENCES `myaccount` (`accountSequence`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `FK_TrainServiceSequence` FOREIGN KEY (`trainServiceSequence_FK`) REFERENCES `trainservice` (`trainServiceSequence`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `CHK_Prize` CHECK ((`prize` > 0)),
  CONSTRAINT `CHK_SeatNum` CHECK (((`seatNum` > 0) and (`seatNum` <= 20))),
  CONSTRAINT `CHK_WagonNum` CHECK (((`wagonNum` > 0) and (`wagonNum` <= 10)))
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
INSERT INTO `ticket` VALUES (5,'8a6b778ce864dc29f154',1,2,'2023-12-29 00:00:00.000000',200,NULL,5),(6,'5842c4372167c3459813',1,2,'2023-12-29 00:00:00.000000',200,1,3),(7,'99d714e0380a439b5431',1,2,'2023-12-29 00:00:00.000000',200,1,7),(8,'c9060d1c74d7f02c99e5',1,2,'2023-12-29 00:00:00.000000',200,1,8),(9,'f01aee1035d4e894ede6',1,2,'2023-12-29 00:00:00.000000',200,1,4);
UNLOCK TABLES;

--
-- Table structure for table `trainservice`
--

DROP TABLE IF EXISTS `trainservice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trainservice` (
  `trainServiceSequence` int NOT NULL AUTO_INCREMENT COMMENT '车次序号，自增主键',
  `trainServiceId` varchar(20) NOT NULL COMMENT '车次号，标识列车的号码',
  `startStation` int DEFAULT NULL COMMENT '起始站，外键，指向站点的序号',
  `endStation` int DEFAULT NULL COMMENT '终点站，外键，指向站点的序号',
  `departureTime` datetime NOT NULL COMMENT '发车时间',
  `personNum` int NOT NULL COMMENT '该趟列车所剩余的人数',
  PRIMARY KEY (`trainServiceSequence`),
  KEY `FK_StartStation` (`startStation`),
  KEY `FK_EndStation` (`endStation`),
  CONSTRAINT `FK_EndStation` FOREIGN KEY (`endStation`) REFERENCES `station` (`stationSequence`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `FK_StartStation` FOREIGN KEY (`startStation`) REFERENCES `station` (`stationSequence`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `CHK_PersonNum` CHECK ((`personNum` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trainservice`
--

LOCK TABLES `trainservice` WRITE;
INSERT INTO `trainservice` VALUES (1,'A0001',1,2,'2024-06-01 07:01:00',200),(2,'A0002',1,3,'2024-06-01 08:01:00',201),(3,'A0003',1,4,'2024-06-01 08:01:00',200),(4,'A0004',1,5,'2024-06-01 09:01:00',200),(5,'A0005',1,6,'2024-06-01 10:01:00',199),(6,'A0006',1,7,'2024-06-01 08:02:00',200),(7,'A0007',1,8,'2023-06-01 09:02:00',200),(8,'A0008',1,9,'2023-06-01 10:02:00',200),(9,'A0009',1,10,'2023-06-01 11:02:00',200),(10,'A00010',1,11,'2023-06-01 08:02:00',200),(11,'A00011',1,12,'2023-06-01 09:04:00',200),(12,'A00012',1,13,'2023-06-01 09:06:00',200),(13,'A00013',1,14,'2023-06-01 10:02:00',200);
UNLOCK TABLES;

--
-- Temporary view structure for view `view_accountlist`
--

DROP TABLE IF EXISTS `view_accountlist`;
/*!50001 DROP VIEW IF EXISTS `view_accountlist`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `view_accountlist` AS SELECT 
 1 AS `账户序号`,
 1 AS `账户ID`,
 1 AS `账户类型`,
 1 AS `支付方式`,
 1 AS `注册时间`,
 1 AS `用户名`,
 1 AS `身份证号`,
 1 AS `联系方式`,
 1 AS `姓名`,
 1 AS `性别`,
 1 AS `年龄`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_alltrainservice`
--

DROP TABLE IF EXISTS `view_alltrainservice`;
/*!50001 DROP VIEW IF EXISTS `view_alltrainservice`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `view_alltrainservice` AS SELECT 
 1 AS `车次序号`,
 1 AS `车次号`,
 1 AS `起点站`,
 1 AS `终点站`,
 1 AS `发车时间`,
 1 AS `剩余人数`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_station`
--

DROP TABLE IF EXISTS `view_station`;
/*!50001 DROP VIEW IF EXISTS `view_station`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `view_station` AS SELECT 
 1 AS `序号`,
 1 AS `站点名`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_ticket`
--

DROP TABLE IF EXISTS `view_ticket`;
/*!50001 DROP VIEW IF EXISTS `view_ticket`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `view_ticket` AS SELECT 
 1 AS `账户序号`,
 1 AS `车票序号`,
 1 AS `车票号`,
 1 AS `车次号`,
 1 AS `起点站`,
 1 AS `终点站`,
 1 AS `发车时间`,
 1 AS `车厢号`,
 1 AS `座位号`,
 1 AS `下单时间`,
 1 AS `售价`,
 1 AS `是否有效`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_validtrainservice`
--

DROP TABLE IF EXISTS `view_validtrainservice`;
/*!50001 DROP VIEW IF EXISTS `view_validtrainservice`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `view_validtrainservice` AS SELECT 
 1 AS `车次序号`,
 1 AS `车次号`,
 1 AS `起点站`,
 1 AS `终点站`,
 1 AS `发车时间`,
 1 AS `剩余人数`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `view_accountlist`
--

/*!50001 DROP VIEW IF EXISTS `view_accountlist`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `view_accountlist` AS select `myaccount`.`accountSequence` AS `账户序号`,`myaccount`.`accountId` AS `账户ID`,(case when (`myaccount`.`accountType` = 1) then '管理员账号' when (`myaccount`.`accountType` = 2) then '会员账号' end) AS `账户类型`,(case when (`myaccount`.`accountType` = 1) then NULL when (`myaccount`.`accountType` = 2) then `myaccount`.`paymentMethod` end) AS `支付方式`,`myaccount`.`registerTime` AS `注册时间`,`myaccount`.`accountName` AS `用户名`,`person`.`identificationId` AS `身份证号`,`person`.`contactInfo` AS `联系方式`,`person`.`name` AS `姓名`,`person`.`gender` AS `性别`,`person`.`age` AS `年龄` from (`myaccount` join `person` on((`myaccount`.`personSequence_FK` = `person`.`personSequence`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_alltrainservice`
--

/*!50001 DROP VIEW IF EXISTS `view_alltrainservice`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `view_alltrainservice` AS select `r`.`trainServiceSequence` AS `车次序号`,`r`.`trainServiceId` AS `车次号`,`t_a`.`stationName` AS `起点站`,`l`.`stationName` AS `终点站`,`r`.`departureTime` AS `发车时间`,`r`.`personNum` AS `剩余人数` from ((`trainservice` `r` left join (select `station`.`stationSequence` AS `stationSequence`,`station`.`stationName` AS `stationName` from `station`) `t_a` on((`t_a`.`stationSequence` = `r`.`startStation`))) left join `station` `l` on((`l`.`stationSequence` = `r`.`endStation`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_station`
--

/*!50001 DROP VIEW IF EXISTS `view_station`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `view_station` AS select `station`.`stationSequence` AS `序号`,`station`.`stationName` AS `站点名` from `station` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_ticket`
--

/*!50001 DROP VIEW IF EXISTS `view_ticket`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `view_ticket` AS select `myaccount`.`accountSequence` AS `账户序号`,`ticket`.`ticketSequence` AS `车票序号`,`ticket`.`ticketId` AS `车票号`,`view_alltrainservice`.`车次号` AS `车次号`,`view_alltrainservice`.`起点站` AS `起点站`,`view_alltrainservice`.`终点站` AS `终点站`,`view_alltrainservice`.`发车时间` AS `发车时间`,`ticket`.`wagonNum` AS `车厢号`,`ticket`.`seatNum` AS `座位号`,`ticket`.`orderTime` AS `下单时间`,`ticket`.`prize` AS `售价`,(case when (`view_alltrainservice`.`发车时间` < curdate()) then '无效' when (`view_alltrainservice`.`发车时间` >= curdate()) then '有效' end) AS `是否有效` from ((`ticket` join `myaccount` on((`ticket`.`accountSequence_FK` = `myaccount`.`accountSequence`))) join `view_alltrainservice` on((`ticket`.`trainServiceSequence_FK` = `view_alltrainservice`.`车次序号`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_validtrainservice`
--

/*!50001 DROP VIEW IF EXISTS `view_validtrainservice`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `view_validtrainservice` AS select `r`.`trainServiceSequence` AS `车次序号`,`r`.`trainServiceId` AS `车次号`,`t_a`.`stationName` AS `起点站`,`l`.`stationName` AS `终点站`,`r`.`departureTime` AS `发车时间`,`r`.`personNum` AS `剩余人数` from ((`trainservice` `r` left join (select `station`.`stationSequence` AS `stationSequence`,`station`.`stationName` AS `stationName` from `station`) `t_a` on((`t_a`.`stationSequence` = `r`.`startStation`))) left join `station` `l` on((`l`.`stationSequence` = `r`.`endStation`))) where (`r`.`departureTime` >= curdate()) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-02 13:33:00
