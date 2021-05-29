INSERT INTO clients (username, password, dni, first_name, last_name, email) values ('pelozo', 'asd', '66666', 'Leo', 'Pelozo', 'leo@pelozo.net');
INSERT INTO users_backoffice (username, password,  first_name, last_name, email) values ('admin', 'admin', 'Señor', 'Admin', 'admin@admin.com');

INSERT INTO clients (username,password,dni,first_name,last_name,email) values ('pepePistola','1234','1645873','pepe','pistolas','pepePistol@gmail.com');

--meters

INSERT INTO meter_brands(name) VALUES('meteoro');
INSERT INTO meter_models(name,brand_id) VALUES('meteractido',1);
INSERT INTO meters(serial_number,model_id,password) VALUES(1234,1,'123456');


------------------------
--tariffs
INSERT INTO tariffs(name,amount) VALUES('voletaso',50.2);


--------------------------
--addresses
INSERT INTO addresses(street,street_number) VALUES('calle falsa',55555);


--------------------
--residences
INSERT INTO residences(client_id,meter_id,tariff_id,address_id) VALUES(1,1,1,1);


---------------------------
--invoices
INSERT INTO invoices(residence_id,is_paid,is_due,due_date, issue_date,kwh_measurement,first_reading,last_reading,total) VALUES(1,1,0,'2021-05-13','2021-06-13',5.0,1.2,3.4,1200);
INSERT INTO invoices(residence_id,is_paid,is_due,due_date, issue_date,kwh_measurement,first_reading,last_reading,total) VALUES(1,0,0,'2020-05-13','2020-06-13',5.0,1.2,3.4,1200);
INSERT INTO invoices(residence_id,is_paid,is_due,due_date,kwh_measurement,first_reading,last_reading,total) VALUES(1,1,0,'2021-05-13',5.0,1.2,3.4,1200);
