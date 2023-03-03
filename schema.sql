

CREATE SCHEMA IF NOT EXISTS `acme_bank` DEFAULT CHARACTER SET latin1 ;
USE `acme_bank` ;

CREATE TABLE IF NOT EXISTS `accounts` (
  `account_id` VARCHAR(100) NOT NULL,
  `name` VARCHAR(50) NULL DEFAULT NULL,
  `balance` DECIMAL(20,2) NULL DEFAULT '0.00',
  PRIMARY KEY (`account_id`));

INSERT INTO `accounts` (`account_id`, `name`, `balance`) VALUES ("V9L3Jd1BBI", 'fred', 100);
INSERT INTO `accounts` (`account_id`, `name`, `balance`) VALUES ("fhRq46Y6vB", 'barney', 300);
INSERT INTO `accounts` (`account_id`, `name`, `balance`) VALUES ("uFSFRqUpJy", 'wilma', 1000);
INSERT INTO `accounts` (`account_id`, `name`, `balance`) VALUES ("ckTV56axff", 'betty', 1000);
INSERT INTO `accounts` (`account_id`, `name`, `balance`) VALUES ("Qgcnwbshbh", 'pebbles', 50);
INSERT INTO `accounts` (`account_id`, `name`, `balance`) VALUES ("if9l185l18", 'bambam', 50);

