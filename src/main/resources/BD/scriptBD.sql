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

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(16) NOT NULL,
  `password` varchar(32) NOT NULL,
  `borrado` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `proveedor`;
CREATE TABLE `proveedor` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `categoria`;
CREATE TABLE `categoria` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `nombre` varchar(128) NOT NULL,
  `borrado` int NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `idUsuario_categoria_FK` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `persona`;
CREATE TABLE `persona` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dni` varchar(9) NOT NULL,
  `nombre` varchar(128) NOT NULL,
  `apellidos` varchar(128) NOT NULL,
  `email` varchar(128) NOT NULL,
  `borrado` int NOT NULL,
  `id_usuario` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `dni_UNIQUE` (`dni`),
  CONSTRAINT `idUsuario_persona_FK` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `cuenta_bancaria`;
CREATE TABLE `cuenta_bancaria` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `id_proveedor` int NOT NULL,
  `id_cuenta` varchar(128) NOT NULL,
  `nombre` varchar(128) NOT NULL,
  `iban` varchar(128) NOT NULL,
  `moneda` varchar(128) NOT NULL,
  `cantidad` DOUBLE(9,2) NOT NULL,
  `fecha` DATE NOT NULL,
  `borrado` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_cuenta_UNIQUE` (`id_cuenta`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  UNIQUE KEY `iban_UNIQUE` (`iban`),
  CONSTRAINT `idUsuario_cuenta_bancaria_FK` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `idProveedor_cuenta_bancaria_FK` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedor` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `movimiento`;
CREATE TABLE `movimiento` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_cuenta_bancaria` int NOT NULL,
  `id_categoria` int NOT NULL,
  `id_transaccion` varchar(128) NOT NULL,
  `cantidad` DOUBLE(9,2) NOT NULL,
  `divisa` varchar(128) NOT NULL,
  `destinatario` varchar(128) NOT NULL,
  `fecha` DATE NOT NULL,
  `borrado` int NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `idUsuario_movimiento_FK` FOREIGN KEY (`id_cuenta_bancaria`) REFERENCES `cuenta_bancaria` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `idCategoria_movimiento_FK` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `criterio`;
CREATE TABLE `criterio` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `id_categoria` int NOT NULL,
  `nombre` varchar(128) NOT NULL,
  `borrado` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  CONSTRAINT `idUsuario_criterio_FK` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `idCategoria_criterio_FK` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `nordigen`;
CREATE TABLE `nordigen` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `id_proveedor` int NOT NULL,
  `secretID` varchar(128) NOT NULL,
  `secretKEY` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `secretID_UNIQUE` (`secretID`),
  UNIQUE KEY `secretKEY_UNIQUE` (`secretKEY`),
  CONSTRAINT `idUsuario_nordigen_FK` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `idProveedor_nordigen_FK` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedor` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `categoria_movimiento`;
CREATE TABLE `categoria_movimiento` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_categoria` int NOT NULL,
  `id_movimiento` int NOT NULL,
  `fecha_asociacion` DATE NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `idMovimiento_categoria_movimiento_FK` FOREIGN KEY (`id_movimiento`) REFERENCES `movimiento` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `idCategoria_categoria_movimiento_FK` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
