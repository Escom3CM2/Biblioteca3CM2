-- MySQL Script generated by MySQL Workbench
-- 05/24/17 00:14:37
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema proyectoBiblioteca
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `proyectoBiblioteca` ;

-- -----------------------------------------------------
-- Schema proyectoBiblioteca
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `proyectoBiblioteca` DEFAULT CHARACTER SET utf8 ;
USE `proyectoBiblioteca` ;

-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`EstadoEstudiante`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`EstadoEstudiante` (
  `idEdoEst` INT NOT NULL,
  `EdoEst` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idEdoEst`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`CP`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`CP` (
  `idCP` INT NOT NULL,
  `CP` VARCHAR(5) NOT NULL,
  PRIMARY KEY (`idCP`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`Delegacion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`Delegacion` (
  `idDelegacion` INT NOT NULL,
  `NombreDel` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idDelegacion`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`Colonia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`Colonia` (
  `idColonia` INT NOT NULL,
  `CP_idCP` INT NOT NULL,
  `Delegacion_idDelegacion` INT NOT NULL,
  `NombreCol` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idColonia`),
  INDEX `fk_Colonia_CP1_idx` (`CP_idCP` ASC),
  INDEX `fk_Colonia_Delegacion1_idx` (`Delegacion_idDelegacion` ASC),
  CONSTRAINT `fk_Colonia_CP1`
    FOREIGN KEY (`CP_idCP`)
    REFERENCES `proyectoBiblioteca`.`CP` (`idCP`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Colonia_Delegacion1`
    FOREIGN KEY (`Delegacion_idDelegacion`)
    REFERENCES `proyectoBiblioteca`.`Delegacion` (`idDelegacion`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`Contacto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`Contacto` (
  `idContacto` INT NOT NULL,
  `Colonia_idColonia` INT NOT NULL,
  `CalleNum` VARCHAR(80) NULL,
  `TelCasa` VARCHAR(45) NULL,
  `TelCelular` VARCHAR(45) NULL,
  `Correo` VARCHAR(45) NULL,
  PRIMARY KEY (`idContacto`),
  INDEX `fk_Contacto_Colonia1_idx` (`Colonia_idColonia` ASC),
  CONSTRAINT `fk_Contacto_Colonia1`
    FOREIGN KEY (`Colonia_idColonia`)
    REFERENCES `proyectoBiblioteca`.`Colonia` (`idColonia`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`Usuario` (
  `Matricula` INT NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  `Tipo` INT NOT NULL,
  PRIMARY KEY (`Matricula`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`Lector`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`Lector` (
  `idLector` INT NOT NULL,
  `Contacto_idContacto` INT NOT NULL,
  `Usuario_Matricula` INT NOT NULL,
  `EstadoBorrado` INT NULL,
  PRIMARY KEY (`idLector`),
  INDEX `fk_Lector_Contacto1_idx` (`Contacto_idContacto` ASC),
  INDEX `fk_Lector_Usuario1_idx` (`Usuario_Matricula` ASC),
  CONSTRAINT `fk_Lector_Contacto1`
    FOREIGN KEY (`Contacto_idContacto`)
    REFERENCES `proyectoBiblioteca`.`Contacto` (`idContacto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Lector_Usuario1`
    FOREIGN KEY (`Usuario_Matricula`)
    REFERENCES `proyectoBiblioteca`.`Usuario` (`Matricula`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`TipoEstudiante`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`TipoEstudiante` (
  `idTipoEs` INT NOT NULL,
  `TipoEst` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idTipoEs`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`Estudiante`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`Estudiante` (
  `Boleta` VARCHAR(45) NOT NULL,
  `EstadoEstudiante_idEdoEst` INT NOT NULL,
  `Lector_idLector` INT NOT NULL,
  `TipoEstudiante_idTipoEs` INT NOT NULL,
  `Nombre` VARCHAR(45) NOT NULL,
  `PrimerApellido` VARCHAR(45) NOT NULL,
  `SegundoApellido` VARCHAR(45) NOT NULL,
  `CURP` VARCHAR(45) NOT NULL,
  `FechaNac` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Boleta`),
  INDEX `fk_Estudiante_EstadoEstudiante1_idx` (`EstadoEstudiante_idEdoEst` ASC),
  INDEX `fk_Estudiante_Lector1_idx` (`Lector_idLector` ASC),
  INDEX `fk_Estudiante_TipoEstudiante1_idx` (`TipoEstudiante_idTipoEs` ASC),
  CONSTRAINT `fk_Estudiante_EstadoEstudiante1`
    FOREIGN KEY (`EstadoEstudiante_idEdoEst`)
    REFERENCES `proyectoBiblioteca`.`EstadoEstudiante` (`idEdoEst`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Estudiante_Lector1`
    FOREIGN KEY (`Lector_idLector`)
    REFERENCES `proyectoBiblioteca`.`Lector` (`idLector`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Estudiante_TipoEstudiante1`
    FOREIGN KEY (`TipoEstudiante_idTipoEs`)
    REFERENCES `proyectoBiblioteca`.`TipoEstudiante` (`idTipoEs`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`Docente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`Docente` (
  `NumEmpleado` VARCHAR(45) NOT NULL,
  `Lector_idLector` INT NOT NULL,
  `Nombre` VARCHAR(45) NOT NULL,
  `PrimerAp` VARCHAR(45) NOT NULL,
  `SegundoAp` VARCHAR(45) NOT NULL,
  `FechaNac` DATE NOT NULL,
  `CURP` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`NumEmpleado`),
  INDEX `fk_Docente_Lector1_idx` (`Lector_idLector` ASC),
  CONSTRAINT `fk_Docente_Lector1`
    FOREIGN KEY (`Lector_idLector`)
    REFERENCES `proyectoBiblioteca`.`Lector` (`idLector`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`RolEmpleado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`RolEmpleado` (
  `idRolEmpleado` INT NOT NULL,
  `RolEmp` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idRolEmpleado`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`EstadoEmpleado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`EstadoEmpleado` (
  `idEdoEmp` INT NOT NULL,
  `EdoEmp` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idEdoEmp`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`Empleado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`Empleado` (
  `idEmpleado` INT NOT NULL,
  `Contacto_idContacto` INT NOT NULL,
  `RolEmpleado_idRolEmpleado` INT NOT NULL,
  `EstadoEmpleado_idEdoEmp` INT NOT NULL,
  `Usuario_Matricula` INT NOT NULL,
  `Nombre` VARCHAR(45) NOT NULL,
  `PrimerAp` VARCHAR(45) NOT NULL,
  `SegundoAp` VARCHAR(45) NOT NULL,
  `FechaAdmision` DATE NOT NULL,
  `EstadoBorrado` INT NULL,
  PRIMARY KEY (`idEmpleado`),
  INDEX `fk_Empleado_Contacto1_idx` (`Contacto_idContacto` ASC),
  INDEX `fk_Empleado_RolEmpleado1_idx` (`RolEmpleado_idRolEmpleado` ASC),
  INDEX `fk_Empleado_EstadoEmpleado1_idx` (`EstadoEmpleado_idEdoEmp` ASC),
  INDEX `fk_Empleado_Usuario1_idx` (`Usuario_Matricula` ASC),
  CONSTRAINT `fk_Empleado_Contacto1`
    FOREIGN KEY (`Contacto_idContacto`)
    REFERENCES `proyectoBiblioteca`.`Contacto` (`idContacto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Empleado_RolEmpleado1`
    FOREIGN KEY (`RolEmpleado_idRolEmpleado`)
    REFERENCES `proyectoBiblioteca`.`RolEmpleado` (`idRolEmpleado`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Empleado_EstadoEmpleado1`
    FOREIGN KEY (`EstadoEmpleado_idEdoEmp`)
    REFERENCES `proyectoBiblioteca`.`EstadoEmpleado` (`idEdoEmp`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Empleado_Usuario1`
    FOREIGN KEY (`Usuario_Matricula`)
    REFERENCES `proyectoBiblioteca`.`Usuario` (`Matricula`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`Libro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`Libro` (
  `ISBN_ID` VARCHAR(45) NOT NULL,
  `Titulo` VARCHAR(45) NOT NULL,
  `Autor1` VARCHAR(45) NOT NULL,
  `Autor2` VARCHAR(45) NULL,
  `Autor3` VARCHAR(45) NULL,
  `Autor4` VARCHAR(45) NULL,
  `Editorial` VARCHAR(45) NOT NULL,
  `FechaPub` VARCHAR(45) NOT NULL,
  `NoPags` INT NOT NULL,
  `Edicion` INT NOT NULL,
  `Precio` DOUBLE NOT NULL,
  `EstadoBorrado` INT NULL,
  PRIMARY KEY (`ISBN_ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`EstadoLibro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`EstadoLibro` (
  `idEdoLibro` INT NOT NULL,
  `EdoLibro` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idEdoLibro`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`Ejemplar`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`Ejemplar` (
  `idEjemplar` INT NOT NULL,
  `Disponibilidad` TINYINT NOT NULL,
  `Observaciones` VARCHAR(45) NULL,
  `EstadoBorrado` INT NULL,
  `Libro_ISBN_ID` VARCHAR(45) NOT NULL,
  `EstadoLibro_idEdoLibro` INT NOT NULL,
  PRIMARY KEY (`idEjemplar`),
  INDEX `fk_Ejemplar_Libro1_idx` (`Libro_ISBN_ID` ASC),
  INDEX `fk_Ejemplar_EstadoLibro1_idx` (`EstadoLibro_idEdoLibro` ASC),
  CONSTRAINT `fk_Ejemplar_Libro1`
    FOREIGN KEY (`Libro_ISBN_ID`)
    REFERENCES `proyectoBiblioteca`.`Libro` (`ISBN_ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Ejemplar_EstadoLibro1`
    FOREIGN KEY (`EstadoLibro_idEdoLibro`)
    REFERENCES `proyectoBiblioteca`.`EstadoLibro` (`idEdoLibro`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`Multa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`Multa` (
  `idMulta` INT NOT NULL,
  `Monto` DOUBLE NOT NULL,
  `FechaInicio` DATE NOT NULL,
  `FechaPago` DATE NOT NULL,
  `Estado` INT NOT NULL,
  PRIMARY KEY (`idMulta`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`Prestamo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`Prestamo` (
  `idPrestamo` INT NOT NULL,
  `Lector_idLector` INT NOT NULL,
  `FechaInicio` DATE NOT NULL,
  `FechaFin` DATE NOT NULL,
  `EstadoPr` INT NOT NULL,
  PRIMARY KEY (`idPrestamo`),
  INDEX `fk_Prestamo_Lector1_idx` (`Lector_idLector` ASC),
  CONSTRAINT `fk_Prestamo_Lector1`
    FOREIGN KEY (`Lector_idLector`)
    REFERENCES `proyectoBiblioteca`.`Lector` (`idLector`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`D_Prestamo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`D_Prestamo` (
  `idD_Prestamo` INT NOT NULL,
  `Ejemplar_idEjemplar` INT NOT NULL,
  `Prestamo_idPrestamo` INT NOT NULL,
  PRIMARY KEY (`idD_Prestamo`),
  INDEX `fk_D_Prestamo_Ejemplar1_idx` (`Ejemplar_idEjemplar` ASC),
  INDEX `fk_D_Prestamo_Prestamo1_idx` (`Prestamo_idPrestamo` ASC),
  CONSTRAINT `fk_D_Prestamo_Ejemplar1`
    FOREIGN KEY (`Ejemplar_idEjemplar`)
    REFERENCES `proyectoBiblioteca`.`Ejemplar` (`idEjemplar`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_D_Prestamo_Prestamo1`
    FOREIGN KEY (`Prestamo_idPrestamo`)
    REFERENCES `proyectoBiblioteca`.`Prestamo` (`idPrestamo`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`Credencial`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`Credencial` (
  `idCredencial` INT NOT NULL,
  `Lector_idLector` INT NOT NULL,
  `FechaEmision` DATE NOT NULL,
  PRIMARY KEY (`idCredencial`),
  INDEX `fk_Credencial_Lector1_idx` (`Lector_idLector` ASC),
  CONSTRAINT `fk_Credencial_Lector1`
    FOREIGN KEY (`Lector_idLector`)
    REFERENCES `proyectoBiblioteca`.`Lector` (`idLector`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`EstadoElemnto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`EstadoElemnto` (
  `idEdoElemnto` INT NOT NULL,
  `EdoElem` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idEdoElemnto`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`Elemento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`Elemento` (
  `idElemento` INT NOT NULL,
  `EstadoElemnto_idEdoElemnto` INT NOT NULL,
  PRIMARY KEY (`idElemento`),
  INDEX `fk_Elemento_EstadoElemnto1_idx` (`EstadoElemnto_idEdoElemnto` ASC),
  CONSTRAINT `fk_Elemento_EstadoElemnto1`
    FOREIGN KEY (`EstadoElemnto_idEdoElemnto`)
    REFERENCES `proyectoBiblioteca`.`EstadoElemnto` (`idEdoElemnto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`TT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`TT` (
  `noTT` VARCHAR(45) NOT NULL,
  `Elemento_idElemento` INT NOT NULL,
  `Titulo` VARCHAR(45) NOT NULL,
  `FechaPresent` DATE NOT NULL,
  `Autor1` VARCHAR(95) NOT NULL,
  `Autor2` VARCHAR(45) NULL,
  `Autor3` VARCHAR(45) NULL,
  `Autor4` VARCHAR(45) NULL,
  `Autor5` VARCHAR(45) NULL,
  `Director1` VARCHAR(45) NOT NULL,
  `Director 2` VARCHAR(45) NULL,
  `EstadoBorrado` INT NULL,
  PRIMARY KEY (`noTT`),
  INDEX `fk_TT_Elemento1_idx` (`Elemento_idElemento` ASC),
  CONSTRAINT `fk_TT_Elemento1`
    FOREIGN KEY (`Elemento_idElemento`)
    REFERENCES `proyectoBiblioteca`.`Elemento` (`idElemento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`Tipo_Audiovisual`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`Tipo_Audiovisual` (
  `idTipoAudiovisual` INT NOT NULL,
  `Nombre` VARCHAR(45) NOT NULL,
  `Libro_ISBNID` VARCHAR(45) NULL,
  PRIMARY KEY (`idTipoAudiovisual`),
  INDEX `fk_Tipo_Audiovisual_1_idx` (`Libro_ISBNID` ASC),
  CONSTRAINT `fk_Tipo_Audiovisual_1`
    FOREIGN KEY (`Libro_ISBNID`)
    REFERENCES `proyectoBiblioteca`.`Libro` (`ISBN_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`AudioVisual`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`AudioVisual` (
  `idAudioVisual` INT NOT NULL,
  `Elemento_idElemento` INT NOT NULL,
  `Titulo` VARCHAR(45) NOT NULL,
  `Autor1` VARCHAR(45) NOT NULL,
  `Autor2` VARCHAR(45) NULL,
  `Autor3` VARCHAR(45) NULL,
  `Autor 4` VARCHAR(45) NULL,
  `FechaPub` VARCHAR(45) NOT NULL,
  `Duracion` VARCHAR(45) NOT NULL,
  `Audiovisual_tipoAudiovisual` INT NOT NULL,
  `Precio` DOUBLE NOT NULL,
  `EstadoBorrado` INT NULL,
  PRIMARY KEY (`idAudioVisual`),
  INDEX `fk_AudioVisual_Elemento1_idx` (`Elemento_idElemento` ASC),
  INDEX `fk_AudioVisual_1_idx` (`Audiovisual_tipoAudiovisual` ASC),
  CONSTRAINT `fk_AudioVisual_Elemento1`
    FOREIGN KEY (`Elemento_idElemento`)
    REFERENCES `proyectoBiblioteca`.`Elemento` (`idElemento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_AudioVisual_1`
    FOREIGN KEY (`Audiovisual_tipoAudiovisual`)
    REFERENCES `proyectoBiblioteca`.`Tipo_Audiovisual` (`idTipoAudiovisual`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`PrestamoOtros`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`PrestamoOtros` (
  `idPrestamoOtros` INT NOT NULL,
  `FechaInicio` DATE NOT NULL,
  `FechFin` DATE NULL,
  `EdoPr` INT NULL,
  `HoraInicio` TIME(6) NULL,
  `HoraFin` TIME(6) NULL,
  `Lector_idLector` INT NOT NULL,
  PRIMARY KEY (`idPrestamoOtros`),
  INDEX `fk_PrestamoOtros_Lector1_idx` (`Lector_idLector` ASC),
  CONSTRAINT `fk_PrestamoOtros_Lector1`
    FOREIGN KEY (`Lector_idLector`)
    REFERENCES `proyectoBiblioteca`.`Lector` (`idLector`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`D_PresOt`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`D_PresOt` (
  `idD_PresOt` INT NOT NULL,
  `Elemento_idElemento` INT NOT NULL,
  `PrestamoOtros_idPrestamoOtros` INT NOT NULL,
  PRIMARY KEY (`idD_PresOt`),
  INDEX `fk_D_PresOt_Elemento1_idx` (`Elemento_idElemento` ASC),
  INDEX `fk_D_PresOt_PrestamoOtros1_idx` (`PrestamoOtros_idPrestamoOtros` ASC),
  CONSTRAINT `fk_D_PresOt_Elemento1`
    FOREIGN KEY (`Elemento_idElemento`)
    REFERENCES `proyectoBiblioteca`.`Elemento` (`idElemento`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_D_PresOt_PrestamoOtros1`
    FOREIGN KEY (`PrestamoOtros_idPrestamoOtros`)
    REFERENCES `proyectoBiblioteca`.`PrestamoOtros` (`idPrestamoOtros`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`Escuela`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`Escuela` (
  `idEscuela` INT NOT NULL,
  `NombreEsc` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idEscuela`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`PrestamoExterno`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`PrestamoExterno` (
  `idPrestamoExterno` INT NOT NULL,
  `Escuela_idEscuela` INT NOT NULL,
  `NombreP` VARCHAR(45) NOT NULL,
  `PrimerApP` VARCHAR(45) NOT NULL,
  `SegundoApP` VARCHAR(45) NOT NULL,
  `Matricula` VARCHAR(45) NOT NULL,
  `Ejemplar_idEjemplar` INT NOT NULL,
  PRIMARY KEY (`idPrestamoExterno`),
  INDEX `fk_PrestamoExterno_Escuela1_idx` (`Escuela_idEscuela` ASC),
  INDEX `fk_PrestamoExterno_Ejemplar1_idx` (`Ejemplar_idEjemplar` ASC),
  CONSTRAINT `fk_PrestamoExterno_Escuela1`
    FOREIGN KEY (`Escuela_idEscuela`)
    REFERENCES `proyectoBiblioteca`.`Escuela` (`idEscuela`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PrestamoExterno_Ejemplar1`
    FOREIGN KEY (`Ejemplar_idEjemplar`)
    REFERENCES `proyectoBiblioteca`.`Ejemplar` (`idEjemplar`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`PrestamoInter`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`PrestamoInter` (
  `idPrestamoInter` INT NOT NULL,
  `Escuela_idEscuela` INT NOT NULL,
  `Lector_idLector` INT NOT NULL,
  `Titulo` VARCHAR(45) NOT NULL,
  `Autor` VARCHAR(45) NOT NULL,
  `Edicion` VARCHAR(45) NOT NULL,
  `Publicacion` INT NOT NULL,
  `Editorial` VARCHAR(45) NOT NULL,
  `FechaPrestamo` DATE NOT NULL,
  PRIMARY KEY (`idPrestamoInter`),
  INDEX `fk_PrestamoInter_Escuela1_idx` (`Escuela_idEscuela` ASC),
  INDEX `fk_PrestamoInter_Lector1_idx` (`Lector_idLector` ASC),
  CONSTRAINT `fk_PrestamoInter_Escuela1`
    FOREIGN KEY (`Escuela_idEscuela`)
    REFERENCES `proyectoBiblioteca`.`Escuela` (`idEscuela`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PrestamoInter_Lector1`
    FOREIGN KEY (`Lector_idLector`)
    REFERENCES `proyectoBiblioteca`.`Lector` (`idLector`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`DatosAcademicos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`DatosAcademicos` (
  `idDatosAcademicos` INT NOT NULL,
  `Semestre` INT NOT NULL,
  `Turno` VARCHAR(45) NOT NULL,
  `Estudiante_Boleta` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idDatosAcademicos`),
  INDEX `fk_DatosAcademicos_Estudiante1_idx` (`Estudiante_Boleta` ASC),
  CONSTRAINT `fk_DatosAcademicos_Estudiante1`
    FOREIGN KEY (`Estudiante_Boleta`)
    REFERENCES `proyectoBiblioteca`.`Estudiante` (`Boleta`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`MultaExtarna`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`MultaExtarna` (
  `idMultaExtarna` INT NOT NULL AUTO_INCREMENT,
  `PrestamoExterno_idPrestamoExterno` INT NOT NULL,
  `Monto` DOUBLE NOT NULL,
  `FechaInicio` DATE NOT NULL,
  `FechaPago` DATE NOT NULL,
  `Estado` INT NOT NULL,
  PRIMARY KEY (`idMultaExtarna`),
  INDEX `fk_MultaExtarna_PrestamoExterno1_idx` (`PrestamoExterno_idPrestamoExterno` ASC),
  CONSTRAINT `fk_MultaExtarna_PrestamoExterno1`
    FOREIGN KEY (`PrestamoExterno_idPrestamoExterno`)
    REFERENCES `proyectoBiblioteca`.`PrestamoExterno` (`idPrestamoExterno`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`TipoMulta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`TipoMulta` (
  `idTipoMulta` INT NOT NULL,
  `Desc` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idTipoMulta`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`DMultaEje`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`DMultaEje` (
  `idDMultaEje` INT NOT NULL,
  `Ejemplar_idEjemplar` INT NOT NULL,
  `Multa_idMulta` INT NOT NULL,
  `TipoMulta_idTipoMulta` INT NOT NULL,
  PRIMARY KEY (`idDMultaEje`),
  INDEX `fk_DMultaEje_Ejemplar1_idx` (`Ejemplar_idEjemplar` ASC),
  INDEX `fk_DMultaEje_Multa1_idx` (`Multa_idMulta` ASC),
  INDEX `fk_DMultaEje_TipoMulta1_idx` (`TipoMulta_idTipoMulta` ASC),
  CONSTRAINT `fk_DMultaEje_Ejemplar1`
    FOREIGN KEY (`Ejemplar_idEjemplar`)
    REFERENCES `proyectoBiblioteca`.`Ejemplar` (`idEjemplar`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DMultaEje_Multa1`
    FOREIGN KEY (`Multa_idMulta`)
    REFERENCES `proyectoBiblioteca`.`Multa` (`idMulta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DMultaEje_TipoMulta1`
    FOREIGN KEY (`TipoMulta_idTipoMulta`)
    REFERENCES `proyectoBiblioteca`.`TipoMulta` (`idTipoMulta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyectoBiblioteca`.`Computo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyectoBiblioteca`.`Computo` (
  `idComputadora` INT NOT NULL,
  `noSerieMonitor` VARCHAR(45) NOT NULL,
  `noSerieCpu` VARCHAR(45) NOT NULL,
  `noSerieTeclado` VARCHAR(45) NOT NULL,
  `noSerieMouse` VARCHAR(45) NOT NULL,
  `PrecioCpu` DOUBLE NOT NULL,
  `PrecioMonitor` VARCHAR(45) NOT NULL,
  `PrecioMouse` VARCHAR(45) NOT NULL,
  `PrecioTeclado` VARCHAR(45) NOT NULL,
  `Elemento_idElemento` INT NULL,
  `EstadoBorrado` INT NULL,
  PRIMARY KEY (`idComputadora`),
  INDEX `fk_Computo_1_idx` (`Elemento_idElemento` ASC),
  CONSTRAINT `fk_Computo_1`
    FOREIGN KEY (`Elemento_idElemento`)
    REFERENCES `proyectoBiblioteca`.`Elemento` (`idElemento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
