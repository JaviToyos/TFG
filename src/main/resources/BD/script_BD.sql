-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: gestorcuentas
-- ------------------------------------------------------
-- Server version	8.1.0



SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

DROP USER IF EXISTS `tfg`@`localhost`;

CREATE USER IF NOT EXISTS 'tfg'@'localhost' IDENTIFIED BY 'tfg';
GRANT ALL ON *.* TO 'tfg'@'localhost';
CREATE USER IF NOT EXISTS 'tfg'@'%' IDENTIFIED BY 'tfg';
GRANT ALL ON *.* TO 'tfg'@'%';
FLUSH PRIVILEGES;

DROP DATABASE IF EXISTS `gestorcuentas`;
CREATE DATABASE `gestorcuentas` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `gestorcuentas`;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `borrado` int DEFAULT NULL,
  `password` varchar(48) DEFAULT NULL,
  `username` varchar(48) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
INSERT INTO `usuario` VALUES (1,0,'3c9c03d6008a5adf42c2a55dd4a1a9f2','javier');
UNLOCK TABLES;

--
-- Table structure for table `persona`
--

DROP TABLE IF EXISTS `persona`;
CREATE TABLE `persona` (
  `id` int NOT NULL AUTO_INCREMENT,
  `apellidos` varchar(255) DEFAULT NULL,
  `borrado` int DEFAULT NULL,
  `dni` varchar(9) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `nombre` varchar(128) DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdvjhjratvktrc6t459mqiidco` (`id_usuario`),
  CONSTRAINT `FKdvjhjratvktrc6t459mqiidco` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `persona`
--

LOCK TABLES `persona` WRITE;
INSERT INTO `persona` VALUES (1,'Toyos Caamaño',0,'53193587S','javiertoyos1@gmail.com','Javier',1);
UNLOCK TABLES;

--
-- Table structure for table `proveedor`
--

DROP TABLE IF EXISTS `proveedor`;
CREATE TABLE `proveedor` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(48) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `proveedor`
--

LOCK TABLES `proveedor` WRITE;
INSERT INTO `proveedor` VALUES (1,'GoCardless');
UNLOCK TABLES;

--
-- Table structure for table `parametro`
--

DROP TABLE IF EXISTS `parametro`;
CREATE TABLE `parametro` (
  `id` int NOT NULL AUTO_INCREMENT,
  `atributo` varchar(255) DEFAULT NULL,
  `valor` varchar(255) DEFAULT NULL,
  `id_proveedor` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrq1qn9uofeca84wpkdgbno094` (`id_proveedor`),
  CONSTRAINT `FKrq1qn9uofeca84wpkdgbno094` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `parametro`
--

LOCK TABLES `parametro` WRITE;
INSERT INTO `parametro` VALUES (1,'secret_id','960a2183-484c-498c-9e56-5dee8728d235',1),(2,'secret_key','9832e831b95217f91b02dada1fef3b2e05f55971c883e40f299384c1cfe2671787ef444274e76383a24161eb36dcd563976c229ffa7bfaa24608a5da8420c2eb',1);
UNLOCK TABLES;

--
-- Table structure for table `cuenta_bancaria`
--

DROP TABLE IF EXISTS `cuenta_bancaria`;
CREATE TABLE `cuenta_bancaria` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_cuenta` varchar(255) DEFAULT NULL,
  `borrado` int DEFAULT NULL,
  `cantidad` double DEFAULT NULL,
  `fecha` datetime(6) DEFAULT NULL,
  `iban` varchar(48) DEFAULT NULL,
  `moneda` varchar(16) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `id_proveedor` int DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKspfxj3o5hadkfik5u7psmgg1p` (`id_proveedor`),
  KEY `FKahgwvt34t8jklsmha8pqlc2to` (`id_usuario`),
  CONSTRAINT `FKahgwvt34t8jklsmha8pqlc2to` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`),
  CONSTRAINT `FKspfxj3o5hadkfik5u7psmgg1p` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

LOCK TABLES `cuenta_bancaria` WRITE;
INSERT INTO `cuenta_bancaria` VALUES (1,'12afdc-34-redc56-23ef3',0,15000,'2023-08-28 17:32:13.505000','ES233456323434565544','EUR','Cuenta corriente',1,1);
UNLOCK TABLES;

--
-- Table structure for table `movimiento`
--

DROP TABLE IF EXISTS `movimiento`;
CREATE TABLE `movimiento` (
  `id` int NOT NULL AUTO_INCREMENT,
  `borrado` int DEFAULT NULL,
  `cantidad` varchar(16) DEFAULT NULL,
  `destinatario` varchar(48) DEFAULT NULL,
  `divisa` varchar(16) DEFAULT NULL,
  `fecha` datetime(6) DEFAULT NULL,
  `id_transaccion` varchar(255) DEFAULT NULL,
  `informacion_movimiento` varchar(255) DEFAULT NULL,
  `id_cuenta_bancaria` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5ot2a5iekngf3sshm8earf56` (`id_cuenta_bancaria`),
  CONSTRAINT `FK5ot2a5iekngf3sshm8earf56` FOREIGN KEY (`id_cuenta_bancaria`) REFERENCES `cuenta_bancaria` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `movimiento`
--

LOCK TABLES `movimiento` WRITE;
INSERT INTO `movimiento` VALUES (1,0,'-120','ES34322332221133','EUR','2023-08-28 17:32:13.669000','1AS234BRE','Compra mercadona',1);
INSERT INTO `movimiento` VALUES (2,0,'-190','ES34322332221144','EUR','2023-08-28 17:32:13.669000','A2S234BLI','Compra para partido baloncesto en Carrefour',1);
INSERT INTO `movimiento` VALUES (3,0,'-30','ES45322332221122','EUR','2023-08-28 17:32:13.669000','3BJ344TRE','Flores ',1);
INSERT INTO `movimiento` VALUES (4,0,'40','ES34322332221155','EUR','2023-08-28 17:32:13.669000','4RE222TRR','Ingreso transferencia',1);
UNLOCK TABLES;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
CREATE TABLE `categoria` (
  `id` int NOT NULL AUTO_INCREMENT,
  `borrado` int DEFAULT NULL,
  `nombre` varchar(48) DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq08txtkyvu0fj9mcrltim01oy` (`id_usuario`),
  CONSTRAINT `FKq08txtkyvu0fj9mcrltim01oy` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
INSERT INTO `categoria` VALUES (1,0,'Supermercado',1),(2,0,'Deportes',1);
UNLOCK TABLES;

--
-- Table structure for table `categoria_movimiento`
--

DROP TABLE IF EXISTS `categoria_movimiento`;
CREATE TABLE `categoria_movimiento` (
  `id_movimiento` int NOT NULL,
  `id_categoria` int NOT NULL,
  PRIMARY KEY (`id_movimiento`,`id_categoria`),
  KEY `FKmk20xfmhw2dunr6gogyvs8rbf` (`id_categoria`),
  CONSTRAINT `FK1vqanengfw8b8u6yyuwmt7m3o` FOREIGN KEY (`id_movimiento`) REFERENCES `movimiento` (`id`),
  CONSTRAINT `FKmk20xfmhw2dunr6gogyvs8rbf` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `categoria_movimiento`
--

LOCK TABLES `categoria_movimiento` WRITE;
INSERT INTO `categoria_movimiento` VALUES (1,1), (2,2), (2,1);
UNLOCK TABLES;

--
-- Table structure for table `criterio`
--

DROP TABLE IF EXISTS `criterio`;
CREATE TABLE `criterio` (
  `id` int NOT NULL AUTO_INCREMENT,
  `borrado` int DEFAULT NULL,
  `nombre` varchar(48) DEFAULT NULL,
  `id_categoria` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjjn040qt342iuj2wdales40r4` (`id_categoria`),
  CONSTRAINT `FKjjn040qt342iuj2wdales40r4` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `criterio`
--

LOCK TABLES `criterio` WRITE;
INSERT INTO `criterio` VALUES (1,0,'Mercadona',1),(2,0,'Carrefour',1),(3,0,'Baloncesto',2);
UNLOCK TABLES;


-- Dump completed
