DROP TABLE IF EXISTS meter_brands;
CREATE TABLE meter_brands
(
    `id`   INT(11)     NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    CONSTRAINT pk_meter_brand PRIMARY KEY (id),
    CONSTRAINT unq_meter_brand UNIQUE (`name`)
);

DROP TABLE IF EXISTS meter_models;
CREATE TABLE meter_models
(
    `id`   INT(11)     NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    brand_id  INT         NOT NULL,
    CONSTRAINT pk_meter_model PRIMARY KEY (id),
    CONSTRAINT fk_model_brand FOREIGN KEY (brand_id) REFERENCES meter_brands (id),
    CONSTRAINT unq_meter_model UNIQUE (`name`)
);


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
);

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
);

DROP TABLE IF EXISTS tariffs;
CREATE TABLE tariffs
(
    `id`   INT(11)     NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    amount FLOAT       NOT NULL,
    CONSTRAINT pk_tariff PRIMARY KEY (id),
    CONSTRAINT unq_tariff UNIQUE (`name`)
);

DROP TABLE IF EXISTS addresses;
CREATE TABLE addresses
(
    `id`          INT(11) NOT NULL AUTO_INCREMENT,
    street        VARCHAR(50),
    street_number INT,
    CONSTRAINT pk_addresses PRIMARY KEY (id)
);

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
);

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
);


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
);


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
);



