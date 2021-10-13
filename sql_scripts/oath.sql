-- DROP DATABASE "oath" ------------
DROP DATABASE IF EXISTS `oath`;
-- ---------------------------------------------------------

-- CREATE DATABASE "oath" ----------
CREATE DATABASE `oath` CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `oath`;
-- ---------------------------------------------------------

-- CREATE TABLE "bank_customer_data" --------------------------------
CREATE TABLE `bank_customer_data` ( 
     `bank_customer_data_id` Int(10) UNSIGNED NOT NULL,
     `client_id` Int(10) UNSIGNED NOT NULL,
     `bp_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
     `customer_id` Int(10) UNSIGNED NULL,
     PRIMARY KEY ( `bank_customer_data_id` ) )
CHARACTER SET = utf8
COLLATE = utf8_general_ci
ENGINE = InnoDB;

-- CREATE TABLE "received_messages" ---------------------------------------
CREATE TABLE `received_messages` (
  `consumer_id` VARCHAR(255),
  `message_id` VARCHAR(255),
  `creation_time` BIGINT,
  PRIMARY KEY(consumer_id, message_id)
);
-- -------------------------------------------------------------
