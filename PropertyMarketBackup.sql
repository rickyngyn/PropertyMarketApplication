CREATE SCHEMA propertymarket;

CREATE TABLE `propertymarket`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `firstname` VARCHAR(45) NULL,
  `lastname` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
  
  CREATE TABLE `propertymarket`.`sales` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `propertyid` INT NULL,
  `clientid` INT NULL,
  `price` VARCHAR(45) NULL,
  `date` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
  
  CREATE TABLE `propertymarket`.`propertyimages` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `propertyid` INT NULL,
  `image` LONGBLOB NULL,
  PRIMARY KEY (`id`));
  
  CREATE TABLE `propertymarket`.`properties` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(45) NULL,
  `owner` INT NULL,
  `price` VARCHAR(45) NULL,
  `address` VARCHAR(45) NULL,
  `bedrooms` INT NULL,
  `bathrooms` INT NULL,
  `description` VARCHAR(45) NULL,
  `squarefeet` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`));
  
  CREATE TABLE `propertymarket`.`personalownerslist` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(45) NULL,
  `lastname` VARCHAR(45) NULL,
  `number` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
  
  CREATE TABLE `propertymarket`.`personalclientlist` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(45) NULL,
  `lastname` VARCHAR(45) NULL,
  `number` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));





