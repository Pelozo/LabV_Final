CREATE DATABASE udee;
USE udee;


CREATE TABLE clients(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	dni VARCHAR(20) NOT NULL,
	firstName VARCHAR(50) NOT NULL,
	lastName VARCHAR(50) NOT NULL,
	CONSTRAINT pk_client PRIMARY KEY(id),
	CONSTRAINT unq_client UNIQUE(dni)
	);
	
CREATE TABLE tariffs(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(50) NOT NULL,
	amount FLOAT NOT NULL,
	CONSTRAINT pk_tariff PRIMARY KEY(id),
	CONSTRAINT UNIQUE(`name`)
	);
	
DROP TABLE meters;
CREATE TABLE meters(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	serial_number INT(11) NOT NULL,
	brand VARCHAR(20) NOT NULL,
	model VARCHAR(20) NOT NULL,
	total_kwh FLOAT NOT NULL,
	CONSTRAINT pk_meter PRIMARY KEY(id),
	CONSTRAINT unq_meter UNIQUE(serial_number)
	);
	
DROP TABLE home;

CREATE TABLE home(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	address VARCHAR(100) NOT NULL,
	client_id INT NOT NULL,
	meter_id INT NOT NULL,
	tariff_id INT NOT NULL,
	CONSTRAINT pk_home PRIMARY KEY(id),
	CONSTRAINT fk_client_home FOREIGN KEY(client_id) REFERENCES clients(id),
	CONSTRAINT fk_meter_home FOREIGN KEY(meter_id) REFERENCES meters(id),
	CONSTRAINT fk_tariff_home FOREIGN KEY(tariff_id) REFERENCES tariffs(id)
	);
