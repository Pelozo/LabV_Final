DROP DATABASE udee;

CREATE DATABASE udee;
USE udee;



DROP TABLE IF EXISTS meter_brands;
CREATE TABLE meter_brands
(
    `id`   INT(11)     NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    CONSTRAINT pk_meter_brand PRIMARY KEY (id),
    CONSTRAINT unq_meter_brand UNIQUE (`name`)
)ENGINE=INNODB;

DROP TABLE IF EXISTS meter_models;
CREATE TABLE meter_models
(
    `id`   INT(11)     NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    brand_id  INT         NOT NULL,
    CONSTRAINT pk_meter_model PRIMARY KEY (id),
    CONSTRAINT fk_model_brand FOREIGN KEY (brand_id) REFERENCES meter_brands (id),
    CONSTRAINT unq_meter_model UNIQUE (`name`)
)ENGINE=INNODB;


DROP TABLE IF EXISTS clients;
CREATE TABLE clients(
    `id`       INT(11)     NOT NULL AUTO_INCREMENT,
    username      VARCHAR(50) NOT NULL,
    `password` VARCHAR(50) NOT NULL,
    dni       VARCHAR(20) NOT NULL,
    email       VARCHAR(20) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    CONSTRAINT pk_client PRIMARY KEY (id),
    CONSTRAINT unq_client_username UNIQUE (username),
    CONSTRAINT unq_client_email UNIQUE (email),
    CONSTRAINT unq_client UNIQUE (dni)
)ENGINE=INNODB;

DROP TABLE IF EXISTS users_backoffice;
CREATE TABLE users_backoffice(
    `id`       INT(11)     NOT NULL AUTO_INCREMENT,
    username      VARCHAR(50) NOT NULL,
    `password` VARCHAR(50) NOT NULL,
    email       VARCHAR(20) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    CONSTRAINT pk_backoffice PRIMARY KEY (id),
    CONSTRAINT unq_pk_backoffice_username UNIQUE (username),
    CONSTRAINT unq_pk_backoffice_email UNIQUE (email)
)ENGINE=INNODB;

DROP TABLE IF EXISTS tariffs;
CREATE TABLE tariffs
(
    `id`   INT(11)     NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    amount FLOAT       NOT NULL,
    CONSTRAINT pk_tariff PRIMARY KEY (id),
    CONSTRAINT unq_tariff UNIQUE (`name`)
)ENGINE=INNODB;

DROP TABLE IF EXISTS addresses;
CREATE TABLE addresses
(
    `id`          INT(11) NOT NULL AUTO_INCREMENT,
    street        VARCHAR(50),
    street_number INT,
    CONSTRAINT pk_addresses PRIMARY KEY (id)
)ENGINE=INNODB;

DROP TABLE IF EXISTS meters;
CREATE TABLE meters
(
    `id`          INT(11) NOT NULL AUTO_INCREMENT,
    serial_number INT(11) NOT NULL,
    model_id      INT(11) NOT NULL,
    PASSWORD      VARCHAR(20) NOT NULL,
    CONSTRAINT pk_meter PRIMARY KEY (id),
    CONSTRAINT unq_meter UNIQUE (serial_number),
    CONSTRAINT fk_model FOREIGN KEY (model_id) REFERENCES meter_models (id)
)ENGINE=INNODB;

DROP TABLE IF EXISTS residences;
CREATE TABLE residences
(
    `id`       INT(11) NOT NULL AUTO_INCREMENT,
    client_id  INT     NOT NULL,
    meter_id   INT     NOT NULL,
    tariff_id  INT     NOT NULL,
    address_id INT     NOT NULL,
    CONSTRAINT pk_home PRIMARY KEY (id),
    CONSTRAINT fk_client_residence FOREIGN KEY (client_id) REFERENCES clients (id),
    CONSTRAINT fk_meter_residence FOREIGN KEY (meter_id) REFERENCES meters (id),
    CONSTRAINT fk_meter_tariff FOREIGN KEY (tariff_id) REFERENCES tariffs (id),
    CONSTRAINT fk_meter_address FOREIGN KEY (address_id) REFERENCES addresses (id)
)ENGINE=INNODB;


DROP TABLE IF EXISTS invoices;
CREATE TABLE invoices
(
    `id`            INT(11) NOT NULL AUTO_INCREMENT,
    residence_id    INT(11) NOT NULL,
    is_paid         BOOLEAN DEFAULT FALSE,
    is_due          BOOLEAN DEFAULT FALSE,
    due_date        DATE NOT NULL,
    issue_date      DATE NOT NULL,
    kwh_measurement FLOAT   NOT NULL,
    first_reading FLOAT NOT NULL,
    last_reading FLOAT NOT NULL,
    total           FLOAT   NOT NULL,
    CONSTRAINT pk_invoice PRIMARY KEY (id),
    CONSTRAINT fk_invoice_home FOREIGN KEY (residence_id) REFERENCES residences (id)
)ENGINE=INNODB;

DROP TABLE IF EXISTS measurements;
CREATE TABLE measurements
(
    `id`         INT(11) NOT NULL AUTO_INCREMENT,
    residence_id INT     NOT NULL,
    kwh_value    FLOAT     NOT NULL,
    `date`       DATETIME    NOT NULL,
    kwh_price    FLOAT   NOT NULL,
    invoice_id   INT,
    CONSTRAINT pk_measurement PRIMARY KEY (id),
    CONSTRAINT fk_measurement_meter FOREIGN KEY (residence_id) REFERENCES residences (id),
    CONSTRAINT fk_measurement_invoice FOREIGN KEY (invoice_id) REFERENCES invoices (id)
)ENGINE=INNODB;

INSERT INTO users_backoffice (username, PASSWORD,  first_name, last_name, email) VALUES ('admin', 'admin', 'Señor', 'Admin', 'admin@admin.com');

/*select * from users_backoffice

select * from clients

select * from meters

SELECT * FROM residences

SELECT * FROM measurements

SELECT * FROM addresses

SELECT * FROM tariffs*/

INSERT INTO clients (username,PASSWORD,dni,first_name,last_name,email) VALUES ('pepePistola','1234','1645873','pepe','pistolas','pepePistol@gmail.com');

INSERT INTO meter_brands(NAME) VALUES('meteoro');
INSERT INTO meter_models(NAME,brand_id) VALUES('meteractido',1);
INSERT INTO meters(serial_number,model_id,PASSWORD) VALUES(1234,1,'123456');

INSERT INTO tariffs(NAME,amount) VALUES('voletaso',50.2);

INSERT INTO addresses(street,street_number) VALUES('calle falsa',55555);


INSERT INTO residences(client_id,meter_id,tariff_id,address_id) VALUES(1,1,1,1);






