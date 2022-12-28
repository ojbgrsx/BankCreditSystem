CREATE DATABASE  IF NOT EXISTS `bankcreditsystem` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bankcreditsystem`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: bankcreditsystem
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `clientId` int NOT NULL AUTO_INCREMENT,
  `clientFName` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `clientLName` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `clientUsername` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `clientPassword` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `clientCash` int DEFAULT NULL,
  PRIMARY KEY (`clientId`),
  UNIQUE KEY `clientUsername` (`clientUsername`),
  UNIQUE KEY `clientId_UNIQUE` (`clientId`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,'Akylbek','Bolsunbekov','ojbgrsx','ojbgrsx',933668),(2,'Azat','Izambaev','padishah','zeppelin',1419996),(4,'John','Doe','john','doe',132641),(6,'Baiaaly','Abaskanov','diversity','diversity',1234135),(7,'Nurislam','Abdykadyrov','nuris','nurislambek',100000),(8,'Ayana','Sagynbekova','docha','khalib',123121),(10,'Aikanysh','Izambaeva','aikakysh','aikanysh',12321),(14,'Stan','Lee','stan','leeber',123213),(15,'Dastan','Sazanov','dastaaaan','dastanchik!@',250000);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credit`
--

DROP TABLE IF EXISTS `credit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `credit` (
  `creditId` int NOT NULL AUTO_INCREMENT,
  `clientId` int NOT NULL,
  `creditType` int NOT NULL,
  `creditState` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `creditAmount` int NOT NULL,
  `creditTakenTime` date DEFAULT (curdate()),
  `creditPaymentTime` date DEFAULT NULL,
  `monthlyPayment` int NOT NULL,
  `leftMonths` int DEFAULT NULL,
  PRIMARY KEY (`creditId`),
  UNIQUE KEY `creditId_UNIQUE` (`creditId`),
  KEY `clientId_idx` (`clientId`),
  KEY `idx_clientId` (`clientId`),
  KEY `idx_credittypeId` (`creditType`) /*!80000 INVISIBLE */,
  CONSTRAINT `creditId` FOREIGN KEY (`clientId`) REFERENCES `client` (`clientId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `credittypeId` FOREIGN KEY (`creditType`) REFERENCES `credittype` (`creditTypeId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credit`
--

LOCK TABLES `credit` WRITE;
/*!40000 ALTER TABLE `credit` DISABLE KEYS */;
INSERT INTO `credit` VALUES (9,1,1,'closed',0,'2022-12-19','2024-07-15',66316,0),(11,2,1,'open',52489,'2022-12-20','2023-12-20',52501,1);
/*!40000 ALTER TABLE `credit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credittype`
--

DROP TABLE IF EXISTS `credittype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `credittype` (
  `creditTypeId` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` blob NOT NULL,
  `interestRate` int NOT NULL,
  PRIMARY KEY (`creditTypeId`),
  UNIQUE KEY `idloantypeId_UNIQUE` (`creditTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credittype`
--

LOCK TABLES `credittype` WRITE;
/*!40000 ALTER TABLE `credittype` DISABLE KEYS */;
INSERT INTO `credittype` VALUES (1,'Consumer credit',_binary 'A consumer loan is a loan provided for the purchase of consumer goods.Such a loan is taken not only for the purchase of durable goods (furniture, cars, etc.), but also for other purchases (mobile phones, household appliances, food), these funds can also be used to pay for contracts in educational institutions, ceremonial events, vacation expenses (travel), house / apartment renovation, etc.',26),(2,'Agrocredit',_binary 'Agrocredits - are intended for individuals engaged in private household plots (personal subsidiary plots), individual entrepreneurs and legal entities operating in the territory of the Kyrgyz Republic and having small and / or medium-sized businesses in agriculture.',30),(3,'Mortgage',_binary 'A mortgage is a pledge of real estate. That is, a mortgage loan means that you take money from the bank at interest (loan), and the guarantee that you will return this money becomes a pledge of your real estate: houses, apartments, land. A mortgage is usually perceived as a loan for the purchase of a home.',16);
/*!40000 ALTER TABLE `credittype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forms`
--

DROP TABLE IF EXISTS `forms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forms` (
  `formId` int NOT NULL AUTO_INCREMENT,
  `clientId` int NOT NULL,
  `initialDate` date NOT NULL DEFAULT (curdate()),
  `birthDate` date NOT NULL,
  `passportNumber` varchar(9) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `citizenship` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `residentialAddress` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `phoneNumber` varchar(12) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `familyStatus` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `workPlace` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `monthlySalary` int NOT NULL,
  `loanType` int NOT NULL,
  `receiveCash` int NOT NULL,
  `requestedPeriod` date NOT NULL,
  `personalProperty` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT 'nothing',
  `currentLoans` tinyint(1) NOT NULL,
  `formState` varchar(15) NOT NULL DEFAULT 'pending',
  PRIMARY KEY (`formId`),
  UNIQUE KEY `formId_UNIQUE` (`formId`),
  UNIQUE KEY `passportNumber_UNIQUE` (`passportNumber`),
  KEY `clientId_idx` (`clientId`),
  KEY `creditType_idx` (`loanType`) /*!80000 INVISIBLE */,
  CONSTRAINT `clientId` FOREIGN KEY (`clientId`) REFERENCES `client` (`clientId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `creditType` FOREIGN KEY (`loanType`) REFERENCES `credittype` (`creditTypeId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forms`
--

LOCK TABLES `forms` WRITE;
/*!40000 ALTER TABLE `forms` DISABLE KEYS */;
INSERT INTO `forms` VALUES (3,1,'2022-12-15','2003-01-24','ID1234567','Kyrgyz','Baytik Baatyra 1/103','996776511560','Alone','Google',150000,1,1000000,'2024-07-15','House,Car',0,'accepted'),(4,2,'2022-12-20','2000-12-14','ID7654321','Kyrgyz','Toktogula 74','996999319928','Married','Beeline',100000,1,500000,'2023-12-20','House,car',0,'accepted');
/*!40000 ALTER TABLE `forms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `worker`
--

DROP TABLE IF EXISTS `worker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `worker` (
  `workerId` int NOT NULL AUTO_INCREMENT,
  `workerFName` varchar(45) NOT NULL,
  `workerLName` varchar(45) NOT NULL,
  `workerUsername` varchar(45) NOT NULL,
  `workerPassword` varchar(45) NOT NULL,
  PRIMARY KEY (`workerId`),
  UNIQUE KEY `workerId_UNIQUE` (`workerId`),
  UNIQUE KEY `workerUsername_UNIQUE` (`workerUsername`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `worker`
--

LOCK TABLES `worker` WRITE;
/*!40000 ALTER TABLE `worker` DISABLE KEYS */;
INSERT INTO `worker` VALUES (1,'Akylbek','Bolsunbekov','ojbgrsx','akylbekk'),(3,'Salim','Nogorbekov','salimbek','salimbek'),(4,'Ayana','Sagynbekova','ayanasagyn','sagynbekova');
/*!40000 ALTER TABLE `worker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'bankcreditsystem'
--

--
-- Dumping routines for database 'bankcreditsystem'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-20  2:44:07
