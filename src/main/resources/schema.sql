drop table if exists meter_brands;
CREATE TABLE meter_brands
(
    `id`   INT(11)     NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    CONSTRAINT pk_meter_brand PRIMARY KEY (id),
    CONSTRAINT unq_meter_brand UNIQUE (`name`)
);

DROP TABLE IF EXISTS meter_models;
create table meter_models
(
    `id`   INT(11)     NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    brand_id  int         not null,
    constraint pk_meter_model primary key (id),
    constraint fk_model_brand foreign key (brand_id) REFERENCES meter_brands (id),
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

DROP TABLE IF EXISTS tariffs;
CREATE TABLE tariffs
(
    `id`   INT(11)     NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    amount FLOAT       NOT NULL,
    CONSTRAINT pk_tariff PRIMARY KEY (id),
    CONSTRAINT unq_tariff UNIQUE (`name`)
);

drop table if exists addresses;
create table addresses
(
    `id`          INT(11) NOT NULL AUTO_INCREMENT,
    street        varchar(50),
    street_number int,
    CONSTRAINT pk_addresses primary key (id)
);

DROP TABLE IF EXISTS meters;
CREATE TABLE meters
(
    `id`          INT(11) NOT NULL AUTO_INCREMENT,
    serial_number INT(11) NOT NULL,
    model_id         int     NOT NULL,
    CONSTRAINT pk_meter PRIMARY KEY (id),
    CONSTRAINT unq_meter UNIQUE (serial_number),
    constraint fk_model foreign key (model_id) references meter_models (id)
);

DROP TABLE IF EXISTS residences;
CREATE TABLE residences
(
    `id`       INT(11) NOT NULL AUTO_INCREMENT,
    client_id  INT     NOT NULL,
    meter_id   INT     NOT NULL,
    tariff_id  INT     NOT NULL,
    address_id int     not null,
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
    kwh_measurement FLOAT   NOT NULL,
    first_reading FLOAT NOT NULL,
    last_reading FLOAT NOT NULL,
    total           FLOAT   NOT NULL,
    CONSTRAINT pk_invoice PRIMARY KEY (id),
    CONSTRAINT fk_invoice_home FOREIGN KEY (residence_id) REFERENCES residences (id)
);

drop table if exists measurements;
create table measurements
(
    `id`         INT(11) NOT NULL AUTO_INCREMENT,
    residence_id int     not null,
    kwh_value    int     not null,
    `date`       date    not null,
    invoice_id   int,
    constraint pk_measurement primary key (id),
    constraint fk_measurement_meter foreign key (residence_id) references residences (id),
    constraint fk_measurement_invoce foreign key (invoice_id) REFERENCES invoices (id)
);

