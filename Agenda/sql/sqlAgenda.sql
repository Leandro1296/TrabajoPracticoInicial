-- Volcando estructura de base de datos para agenda
CREATE DATABASE IF NOT EXISTS `agenda` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `agenda`;

-- Volcando estructura para tabla agenda.localidades
CREATE TABLE IF NOT EXISTS `localidades` (
  `idLocalidad` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`idLocalidad`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- Volcando estructura para tabla agenda.tiposdecontacto
CREATE TABLE IF NOT EXISTS `tiposdecontacto` (
  `idTipoDeContacto` int(11) NOT NULL AUTO_INCREMENT,
  `Tipo` varchar(50) NOT NULL,
  PRIMARY KEY (`idTipoDeContacto`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- Volcando estructura para tabla agenda.personas
CREATE TABLE IF NOT EXISTS `personas` (
  `idPersona` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) NOT NULL,
  `Telefono` varchar(20) NOT NULL,
  `Calle` varchar(50) NOT NULL,
  `Altura` int(11) NOT NULL,
  `Piso` int(11) DEFAULT NULL,
  `Departamento` varchar(50) DEFAULT NULL,
  `Localidad` int(11) NOT NULL,
  `Mail` varchar(50) NOT NULL,
  `fechaDeNacimiento` date NOT NULL,
  `Tipo` int(11) NOT NULL,
  PRIMARY KEY (`idPersona`),
  KEY `FK_localidad` (`Localidad`),
  KEY `FK_tipoDeContacto` (`Tipo`),
  CONSTRAINT `FK_localidad` FOREIGN KEY (`Localidad`) REFERENCES `localidades` (`idLocalidad`),
  CONSTRAINT `FK_tipoDeContacto` FOREIGN KEY (`Tipo`) REFERENCES `tiposdecontacto` (`idTipoDeContacto`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
