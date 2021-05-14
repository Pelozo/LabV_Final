DROP DATABASE udee;
CREATE DATABASE udee;
USE udee;

DROP TABLE IF EXISTS meter_brands;
CREATE TABLE meter_brands(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(50) NOT NULL,
	CONSTRAINT pk_meter_brand PRIMARY KEY(id),
	CONSTRAINT UNIQUE(`name`)
	);

DROP TABLE IF EXISTS meter_models;
CREATE TABLE meter_models(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(50) NOT NULL,
	brand INT NOT NULL,
	CONSTRAINT pk_meter_model PRIMARY KEY(id),
	CONSTRAINT fk_model_brand FOREIGN KEY(brand) REFERENCES meter_brands(id),	
	CONSTRAINT UNIQUE(`name`)
	);

DROP TABLE IF EXISTS users;
CREATE TABLE users(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	email VARCHAR(50) NOT NULL,
	`password` VARCHAR(50) NOT NULL,
	CONSTRAINT pk_users PRIMARY KEY(id),
	CONSTRAINT unq_client_email UNIQUE(email)
	);

	
DROP TABLE IF EXISTS clients;
CREATE TABLE clients(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	user_id INT NOT NULL,
	dni VARCHAR(20) NOT NULL,
	firstName VARCHAR(50) NOT NULL,
	lastName VARCHAR(50) NOT NULL,
	CONSTRAINT pk_client PRIMARY KEY(id),
	CONSTRAINT fk_clients FOREIGN KEY(user_id) REFERENCES users(id),
	CONSTRAINT unq_client UNIQUE(dni)	
	);

DROP TABLE IF EXISTS tariffs;	
CREATE TABLE tariffs(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(50) NOT NULL,
	amount FLOAT NOT NULL,
	isDeleted BOOLEAN DEFAULT FALSE,
	CONSTRAINT pk_tariff PRIMARY KEY(id),
	CONSTRAINT UNIQUE(`name`)
	);
	
DROP TABLE IF EXISTS addresses;
CREATE TABLE addresses(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	street VARCHAR(50),
	street_number INT,
	postal_code INT,
	isDeleted BOOLEAN DEFAULT FALSE,
	CONSTRAINT pk_addresses PRIMARY KEY(id)
	);

DROP TABLE IF EXISTS meters;
CREATE TABLE meters(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	serial_number INT(11) NOT NULL,
	`password` VARCHAR(50) NOT NULL,
	model INT NOT NULL,
	isDeleted BOOLEAN DEFAULT FALSE,
	CONSTRAINT pk_meter PRIMARY KEY(id),
	CONSTRAINT unq_meter UNIQUE(serial_number),
	CONSTRAINT fk_model FOREIGN KEY(model) REFERENCES meter_models(id)
	);
	
DROP TABLE IF EXISTS residences;
CREATE TABLE residences(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	client_id INT NOT NULL,
	meter_id INT NOT NULL,
	tariff_id INT NOT NULL,
	address_id INT NOT NULL,
	CONSTRAINT pk_home PRIMARY KEY(id),
	CONSTRAINT fk_client_residence FOREIGN KEY(client_id) REFERENCES clients(id),
	CONSTRAINT fk_meter_residence FOREIGN KEY(meter_id) REFERENCES meters(id),
	CONSTRAINT fk_meter_tariff FOREIGN KEY(tariff_id) REFERENCES tariffs(id),
	CONSTRAINT fk_meter_address FOREIGN KEY(address_id) REFERENCES addresses(id)
	);
	

DROP TABLE IF EXISTS invoices;
CREATE TABLE invoices(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	residence_id INT(11) NOT NULL,
	isPaid BOOLEAN DEFAULT FALSE,
	isDue BOOLEAN DEFAULT FALSE,
	kwh_measurement FLOAT NOT NULL,
	total FLOAT NOT NULL,
	CONSTRAINT pk_invoice PRIMARY KEY(id),
	CONSTRAINT fk_invoice_home FOREIGN KEY(residence_id) REFERENCES residences(id)
	);

DROP TABLE IF EXISTS measurements;
CREATE TABLE measurements(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	residence_id INT NOT NULL,
	kwh_value INT NOT NULL,
	`date` DATE NOT NULL,
	invoice_id INT,
	CONSTRAINT pk_measurement PRIMARY KEY(id),
	CONSTRAINT fk_measurement_meter FOREIGN KEY(residence_id) REFERENCES residences(id),
	CONSTRAINT fk_measurement_invoce FOREIGN KEY(invoice_id) REFERENCES invoices(id)
	);

