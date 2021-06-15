DROP PROCEDURE IF EXISTS create_invoices

DELIMITER //
CREATE PROCEDURE create_invoices(p_residence_id INT)
store_create_invoice:
BEGIN

	DECLARE v_first_reading, v_last_reading, v_total, v_kwh_measurement FLOAT(10);
	DECLARE v_first_date, v_last_date DATETIME DEFAULT 0;
		
	START TRANSACTION;
	
	SELECT 
	MIN(m.date) AS min_date,MAX(m.date), SUM(m.kwh_price) INTO v_first_date, v_last_date, v_total
	FROM measurements m
	JOIN residences r
	ON r.id = m.residence_id
	JOIN clients c
	ON r.client_id = c.id
	JOIN meters mt
	ON r.meter_id = mt.id
	WHERE m.invoice_id IS NULL AND r.id = p_residence_id
	GROUP BY  r.meter_id;
	
	IF(v_total IS NULL) THEN
		LEAVE store_create_invoice;
	END IF;
		
	
	-- ver si lo podemos hacer mas performante
	SELECT MIN(kwh_value),MAX(kwh_value) INTO v_first_reading, v_last_reading 
	FROM measurements 
	WHERE `date` BETWEEN v_first_date AND v_last_date AND residence_id = p_residence_id;
	
	
	
	-- aca evitamos que cuando haya una sola medicion, no muestre un 0 en kwh_measurement
	IF(v_first_reading = v_last_reading) THEN
		SET v_kwh_measurement = v_last_reading;
	ELSE
		SET v_kwh_measurement = (v_last_reading - v_first_reading);
	END IF;
	
	INSERT INTO invoices(residence_id,is_paid,is_due,due_date,issue_date,kwh_measurement,first_reading,last_reading,total)
	VALUES (p_residence_id,FALSE,FALSE,DATE_ADD(DATE(NOW()),INTERVAL 30 DAY),DATE(NOW()),v_kwh_measurement,v_first_reading,v_last_reading,v_total);
	
	UPDATE measurements SET invoice_id = LAST_INSERT_ID() WHERE residence_id = p_residence_id AND invoice_id IS NULL;
	
	COMMIT;

END;

DELIMITER;

CALL create_invoices(1)

SELECT * FROM measurements
SELECT * FROM measurements WHERE residence_id = 4
SELECT * FROM invoices

INSERT INTO `measurements` (`id`, `residence_id`, `kwh_value`, `date`, `kwh_price`, `invoice_id`) VALUES('196',1,100,'2021-05-28 18:31:36',5020,NULL);


DROP PROCEDURE IF EXISTS create_all_invoices;

DELIMITER //
CREATE PROCEDURE create_all_invoices()
BEGIN

	DECLARE v_residence_id INT;
	DECLARE v_handler INT DEFAULT 0;
	-- declare cur_residences cursor for select residence_idfrom measurements group by residence_id;
	DECLARE cur_residences CURSOR FOR SELECT id FROM residences;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_handler = 1;
					 
	OPEN cur_residences;
	
	residences:
	LOOP
		FETCH cur_residences INTO v_residence_id;
		IF v_handler = 1 THEN 
			LEAVE residences;
		END IF;
		CALL create_invoices(v_residence_id);
	END LOOP residences;
	
	CLOSE cur_residences;
	
END;

DELIMITER ;

DROP EVENT IF EXISTS invoice_event

DELIMITER //
CREATE EVENT invoice_event ON SCHEDULE EVERY 1 MONTH
STARTS "2021-07-01 00:00:00" DO
BEGIN 
	CALL create_all_invoices();
END;

DELIMITER ;

SHOW EVENTS

EXPLAIN SELECT * 
FROM measurements m
JOIN residences r 
ON r.id = m.residence_id
JOIN clients c
ON c.id = r.client_id
JOIN meters mt
ON r.meter_id = mt.id
WHERE m.date BETWEEN "2022-04-17 16:32:08" AND "2022-05-10 14:42:34"


-- esto demuestra que el indice existe y puede ser utilizado
EXPLAIN SELECT * FROM measurements WHERE `date` BETWEEN "2021-06-17 16:32:08" AND "2021-06-17 16:45:08" AND residence_id = 8;

-- indice por id de residencia
CREATE INDEX idx_measurements_residence
ON measurements (residence_id) 
USING HASH;



CREATE INDEX idx_measurements_date
ON measurements (`date`)
USING BTREE;

SHOW INDEX measurements

SELECT * FROM measurements WHERE residence_id = 2