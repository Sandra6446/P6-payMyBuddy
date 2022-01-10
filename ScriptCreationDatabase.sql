SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema pay_my_buddy
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `pay_my_buddy` ;

-- -----------------------------------------------------
-- Schema pay_my_buddy
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `pay_my_buddy` DEFAULT CHARACTER SET utf8 ;
USE `pay_my_buddy` ;

-- -----------------------------------------------------
-- Utilisateur
-- -----------------------------------------------------
CREATE USER 'openclassrooms'@'localhost' IDENTIFIED BY 'OC';
GRANT ALL PRIVILEGES ON pay_my_buddy.* TO 'openclassrooms'@'localhost';
FLUSH PRIVILEGES;

-- -----------------------------------------------------
-- Table `pay_my_buddy`.`utilisateur`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pay_my_buddy`.`utilisateur` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(150) NOT NULL,
  `nom` VARCHAR(100) NOT NULL,
  `prenom` VARCHAR(100) NOT NULL,
  `mot_de_passe` VARCHAR(255) NOT NULL,
  `solde` DECIMAL(6,2) NULL,
  `banque` VARCHAR(50) NOT NULL,
  `iban` CHAR(27) NOT NULL,
  `bic` VARCHAR(11) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `email_UNIQUE` ON `pay_my_buddy`.`utilisateur` (`email` ASC);

-- -----------------------------------------------------
-- Table `pay_my_buddy`.`facture`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pay_my_buddy`.`facture` (
  `id` VARCHAR(20) NOT NULL,
  `date` DATETIME NOT NULL,
  `montant` DECIMAL(4,2) NOT NULL DEFAULT 0.00,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `pay_my_buddy`.`reseau`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pay_my_buddy`.`reseau` (
  `utilisateur_email` VARCHAR(150) NOT NULL,
  `contact_email` VARCHAR(150) NOT NULL,
  PRIMARY KEY (`utilisateur_email`, `contact_email`),
  CONSTRAINT `fk_utilisateur_email`
    FOREIGN KEY (`utilisateur_email`)
    REFERENCES `pay_my_buddy`.`utilisateur` (`email`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_contact_email`
    FOREIGN KEY (`contact_email`)
    REFERENCES `pay_my_buddy`.`utilisateur` (`email`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `fk_contact_email_idx` ON `pay_my_buddy`.`reseau` (`contact_email` ASC);


-- -----------------------------------------------------
-- Table `pay_my_buddy`.`transfert`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pay_my_buddy`.`transfert` (
  `utilisateur_email` VARCHAR(150) NOT NULL,
  `destinataire_email` VARCHAR(150) NOT NULL,
  `date` DATETIME NOT NULL,
  `montant` INT UNSIGNED NOT NULL,
  `description` VARCHAR(50) NOT NULL,
  `date_prelevement` DATETIME NULL,
  `facture_ id` VARCHAR(20) NULL,
  PRIMARY KEY (`utilisateur_email`, `destinataire_email`, `date`),
  CONSTRAINT `fk_facture_id`
    FOREIGN KEY (`facture_ id`)
    REFERENCES `pay_my_buddy`.`facture` (`id`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_emetteur_email`
    FOREIGN KEY (`utilisateur_email`)
    REFERENCES `pay_my_buddy`.`utilisateur` (`email`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_destinataire_email`
    FOREIGN KEY (`destinataire_email`)
    REFERENCES `pay_my_buddy`.`utilisateur` (`email`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `fk_facture_idx` ON `pay_my_buddy`.`transfert` (`facture_ id` ASC);

CREATE INDEX `fk_destinataire_email_idx` ON `pay_my_buddy`.`transfert` (`destinataire_email` ASC);

-- -----------------------------------------------------
-- Remplissage des tables pour les tests
-- -----------------------------------------------------

INSERT INTO `utilisateur` (`email`,`nom`,`prenom`,`mot_de_passe`,`solde`,`banque`,`iban`,`bic`) 
VALUES 
('sarah@email.com','Dupond','Sarah','$2a$10$5Di9nQ1GyXzAKtaaQQ87j.6Bn7zduJmJamc9TTqYdqNXjGSXvQ8yq',50,'Ma banque','FR73XXX','XXX'),
('louis@email.com','Durand','Louis','$2a$10$A7yc1/7Cxz37V2GbwG1vTOg/3Uav9xCbBRcV3wZLvr1UuiChmZcqC',20,'Ma banque','FR73XXX','XXX'),
('myriam@email.com','Smith','Myriam','$2a$10$jR8RyZQnYJCjx3ghkvo9W.5gn1kfyC1IBof9UIOao./u4U.AAJ752',10,'Ma banque','FR73XXX','XXX'),
('rachel@email.com','Dos Santos','Rachel','$2a$10$FAFvDwWjKnHXyHjEuBExs.T.GFgq1OHuZhHcT6uux1ZJuw16lHP.S',50,'Ma banque','FR73XXX','XXX'),
('thomas@email.com','Morin','Thomas','$2a$10$H1iD40eyfWDps/0o5dkOfuoJ3sGyire.C4VEv6sPUoOGI5WFZ8OSW',15,'Ma banque','FR73XXX','XXX');

INSERT INTO `reseau`
VALUES
( 'sarah@email.com','louis@email.com'),
( 'sarah@email.com','myriam@email.com'),
( 'sarah@email.com','rachel@email.com'),
( 'sarah@email.com','thomas@email.com'),
( 'thomas@email.com','sarah@email.com'),
( 'thomas@email.com','rachel@email.com'),
( 'thomas@email.com','myriam@email.com'),
( 'myriam@email.com','thomas@email.com'),
( 'rachel@email.com','louis@email.com'),
( 'rachel@email.com','myriam@email.com'),
( 'rachel@email.com','sarah@email.com'),
( 'rachel@email.com','thomas@email.com');

INSERT INTO `transfert`(`utilisateur_email`,`destinataire_email`,`date`,`montant`,`description`)
VALUES
('sarah@email.com','myriam@email.com','2021-12-14 12:07:55',12,'Cadeaux de Noël'),
('sarah@email.com','rachel@email.com','2022-01-03 09:51:54',21,'Restaurant'),
('rachel@email.com','sarah@email.com','2022-01-03 14:45:11',12,'Cinema'),
('thomas@email.com','sarah@email.com','2021-12-14 12:13:32',25,'Cadeaux');


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
