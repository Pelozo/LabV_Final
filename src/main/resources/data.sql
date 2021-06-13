INSERT INTO clients (username, password, dni, first_name, last_name, email) values ('pelozo', 'asd', '66666', 'Leo', 'Pelozo', 'leo@pelozo.net');
INSERT INTO users_backoffice (username, password,  first_name, last_name, email) values ('admin', 'admin', 'Señor', 'Admin', 'admin@admin.com');

INSERT INTO clients (username,password,dni,first_name,last_name,email) values ('pepePistola','1234','1645873','pepe','pistolas','pepePistol@gmail.com');

INSERT INTO clients (username,PASSWORD,dni,first_name,last_name,email) VALUES ('a','1234','164587','pepe','pistolas','pepePisto@gmail.com');
INSERT INTO clients (username,PASSWORD,dni,first_name,last_name,email) VALUES ('b','1234','164583','pepe','pistolas','pepePistl@gmail.com');
INSERT INTO clients (username,PASSWORD,dni,first_name,last_name,email) VALUES ('f','1234','164573','pepe','pistolas','pepePisol@gmail.com');
INSERT INTO clients (username,PASSWORD,dni,first_name,last_name,email) VALUES ('c','1234','164873','pepe','pistolas','pepePitol@gmail.com');
INSERT INTO clients (username,PASSWORD,dni,first_name,last_name,email) VALUES ('d','1234','165873','pepe','pistolas','pepePstol@gmail.com');
INSERT INTO clients (username,PASSWORD,dni,first_name,last_name,email) VALUES ('e','1234','145873','pepe','pistolas','pepeistol@gmail.com');

--meters

INSERT INTO meter_brands(name) VALUES('meteoro');
INSERT INTO meter_models(name,brand_id) VALUES('meteractido',1);
INSERT INTO meters(serial_number,model_id,password) VALUES(1234,1,'123456');
INSERT INTO meters(serial_number,model_id,PASSWORD) VALUES(12345,1,'123456');
INSERT INTO meters(serial_number,model_id,PASSWORD) VALUES(12346,1,'123456');
INSERT INTO meters(serial_number,model_id,PASSWORD) VALUES(123,1,'123456');
INSERT INTO meters(serial_number,model_id,PASSWORD) VALUES(124,1,'123456');
INSERT INTO meters(serial_number,model_id,PASSWORD) VALUES(134,1,'123456');
INSERT INTO meters(serial_number,model_id,PASSWORD) VALUES(234,1,'123456');
INSERT INTO meters(serial_number,model_id,PASSWORD) VALUES(11234,1,'123456');


------------------------
--tariffs
INSERT INTO tariffs(name,amount) VALUES('voletaso',50.2);


--------------------------
--addresses
INSERT INTO addresses(street,street_number) VALUES('calle falsa',55555);
INSERT INTO addresses(street,street_number) VALUES('calle fals',55555);
INSERT INTO addresses(street,street_number) VALUES('calle fala',55555);
INSERT INTO addresses(street,street_number) VALUES('calle fasa',55555);
INSERT INTO addresses(street,street_number) VALUES('calle flsa',55555);
INSERT INTO addresses(street,street_number) VALUES('calle alsa',55555);
INSERT INTO addresses(street,street_number) VALUES('callefalsa',55555);
INSERT INTO addresses(street,street_number) VALUES('call falsa',55555);


--------------------
--residences
INSERT INTO residences(client_id,meter_id,tariff_id,address_id) VALUES(1,1,1,1);
INSERT INTO residences(client_id,meter_id,tariff_id,address_id) VALUES(1,8,1,8);
INSERT INTO residences(client_id,meter_id,tariff_id,address_id) VALUES(2,2,1,2);
INSERT INTO residences(client_id,meter_id,tariff_id,address_id) VALUES(3,3,1,3);
INSERT INTO residences(client_id,meter_id,tariff_id,address_id) VALUES(3,4,1,4);
INSERT INTO residences(client_id,meter_id,tariff_id,address_id) VALUES(5,5,1,5);
INSERT INTO residences(client_id,meter_id,tariff_id,address_id) VALUES(6,6,1,6);
INSERT INTO residences(client_id,meter_id,tariff_id,address_id) VALUES(7,7,1,7);


---------------------------
--invoices
INSERT INTO invoices(residence_id,is_paid,is_due,due_date, issue_date,kwh_measurement,first_reading,last_reading,total) VALUES(1,1,0,'2021-05-13','2021-06-13',5.0,1.2,3.4,1200);
INSERT INTO invoices(residence_id,is_paid,is_due,due_date, issue_date,kwh_measurement,first_reading,last_reading,total) VALUES(1,0,0,'2020-05-13','2020-06-13',5.0,1.2,3.4,1200);


---------------------------------------
--measurements
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('196',1,100,'2021-05-28 18:31:36',5020,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('197',2,100.593,'2021-05-28 18:31:37',29.7714,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('198',2,100.675,'2021-05-28 18:31:38',4.10342,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('199',2,100.808,'2021-05-28 18:31:39',6.69093,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('200',2,101.391,'2021-05-28 18:31:40',29.272,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('201',2,101.914,'2021-05-28 18:31:41',26.2655,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('202',2,102.398,'2021-05-28 18:31:42',24.2969,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('203',2,103.365,'2021-05-28 18:31:43',48.5006,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('204',2,103.941,'2021-05-28 18:31:44',28.9323,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('205',2,104.889,'2021-05-28 18:31:45',47.6033,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('206',3,105.464,'2021-05-28 18:31:46',28.8671,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('207',3,105.765,'2021-05-28 18:31:47',15.0828,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('208',3,106.283,'2021-05-28 18:31:48',26.0441,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('209',3,106.878,'2021-05-28 18:31:49',29.8292,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('210',3,106.917,'2021-05-28 18:31:50',1.98775,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('211',3,107.636,'2021-05-28 18:31:51',36.0973,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('212',3,107.659,'2021-05-28 18:31:52',1.13482,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('213',3,108.403,'2021-05-28 18:31:53',37.3298,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('214',3,108.907,'2021-05-28 18:31:54',25.3413,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('215',3,109.019,'2021-05-28 18:31:55',5.62735,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('216',4,109.238,'2021-05-28 18:31:56',10.9747,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('217',4,109.645,'2021-05-28 18:31:57',20.4037,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('218',4,110.099,'2021-05-28 18:31:58',22.8323,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('219',6,111.071,'2021-05-28 18:31:59',48.7936,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('220',6,111.277,'2021-05-28 18:32:00',10.3321,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('221',6,111.928,'2021-05-28 18:32:01',32.6741,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('222',6,112.179,'2021-05-28 18:32:02',12.5986,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('223',6,112.725,'2021-05-28 18:32:04',27.3853,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('224',6,113.331,'2021-05-28 18:32:05',30.4371,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('225',7,113.447,'2021-05-28 18:32:06',5.82996,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('226',1,113.653,'2021-05-28 18:32:07',10.3229,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('227',7,114.227,'2021-05-28 18:32:08',28.8537,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('228',7,114.619,'2021-05-28 18:32:09',19.6373,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('229',1,114.793,'2021-05-28 18:32:10',8.74302,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('230',7,115.615,'2021-05-28 18:32:11',41.2525,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('231',7,115.681,'2021-05-28 18:32:12',3.33321,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('232',1,116.337,'2021-05-28 18:32:13',32.9235,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('233',1,117.081,'2021-05-28 18:32:14',37.3088,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('234',7,117.879,'2021-05-28 18:32:15',40.1146,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('235',1,118.582,'2021-05-28 18:32:16',35.2827,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('236',1,119.026,'2021-05-28 18:32:17',22.2735,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('237',8,120.455,'2021-05-28 18:32:18',48.9124,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('238',8,120.784,'2021-05-28 18:32:19',39.3769,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('239',8,121.341,'2021-05-28 18:32:20',27.9583,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('240',8,121.392,'2021-05-28 18:32:21',2.45692,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('241',1,121.501,'2021-05-28 18:32:22',5.54041,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('242',1,121.661,'2021-05-28 18:32:23',8.06512,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('243',1,122.166,'2021-05-28 18:32:24',25.3378,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('244',1,122.331,'2021-05-28 18:32:25',8.28956,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('245',1,122.442,'2021-05-28 18:32:26',5.58216,NULL);
insert into `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) values('246',1,122.989,'2021-05-28 18:32:27',27.4301,NULL);