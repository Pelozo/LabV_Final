DROP PROCEDURE IF EXISTS create_invoice

-- sotred procedure para crear la factura de una residencia
DELIMITER //
CREATE PROCEDURE create_invoice(p_residence_id INT)
-- le creamos una etiqueta al procedure para poder salir si hace falta del mismo
store_create_invoice:
BEGIN

	DECLARE v_first_reading, v_last_reading, v_total, v_kwh_measurement FLOAT(10);
	DECLARE v_first_date, v_last_date DATETIME DEFAULT 0;
	
		
	
	-- selecctionamos la fecha de la primera y ultima medicion y la suma del precio a pagar 
	-- de todas las mediciones que no hayan sido facturadas de una determinada residencia
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
	
	-- si no hay mediciones 
	IF(v_total IS NULL) THEN
	-- salimos del stored procedure
		LEAVE store_create_invoice;
	END IF;
		
	
	-- conseguimos los valores de la primera y ultima medicion
	SELECT MIN(kwh_value),MAX(kwh_value) INTO v_first_reading, v_last_reading 
	FROM measurements 
	WHERE `date` BETWEEN v_first_date AND v_last_date AND residence_id = p_residence_id;
	
	
	
	-- aca evitamos que cuando haya una sola medicion, no muestre un 0 en kwh_measurement
	IF(v_first_reading = v_last_reading) THEN
		SET v_kwh_measurement = v_last_reading;
	ELSE
		SET v_kwh_measurement = (v_last_reading - v_first_reading);
	END IF;
	
	-- estos dos statements tienen que ser atomicos por lo tanto utilizamos transacciones
	START TRANSACTION;
	
	-- creamos la factura
	INSERT INTO invoices(residence_id,is_paid,is_due,due_date,issue_date,kwh_measurement,first_reading,last_reading,total)
	VALUES (p_residence_id,FALSE,FALSE,DATE_ADD(DATE(NOW()),INTERVAL 30 DAY),DATE(NOW()),v_kwh_measurement,v_first_reading,v_last_reading,v_total);
	
	-- actualiza el id de la factura al que pertenece la medicion
	UPDATE measurements SET invoice_id = LAST_INSERT_ID() WHERE residence_id = p_residence_id AND invoice_id IS NULL;
	
	COMMIT;

END;

DELIMITER;

DROP PROCEDURE IF EXISTS create_all_invoices;

DELIMITER //
CREATE PROCEDURE create_all_invoices()
BEGIN

	DECLARE v_residence_id INT;
	DECLARE v_handler INT DEFAULT 0;
	
	-- declare cur_residences cursor for select residence_idfrom measurements group by residence_id;
	-- declaramos un cursor para poder iterar por cada una de las residencias
	DECLARE cur_residences CURSOR FOR SELECT id FROM residences;
	
	-- declaramos una variable para verificar cuando la lectura del registro sea 0
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_handler = 1;
	
	-- abrimos el cursor				 
	OPEN cur_residences;
	
	
	-- creamos un loop llamado residences
	residences:
	LOOP
	-- lo que el cursor trae lo guardamos en una variable
		FETCH cur_residences INTO v_residence_id;
		-- verificamos si el cursor trajo datos
		IF v_handler = 1 THEN 
		-- si no trajo nada salimos del loop
			LEAVE residences;
		END IF;
		
	-- llamamos al strored procedure para crear una invoice de la residencia que trajo el cursor
		CALL create_invoice(v_residence_id);
		
	-- terminamos el loop
	END LOOP residences;
	
	-- cerramos el cursor para liberar los registros tomados
	CLOSE cur_residences;
	
END;

DELIMITER ;

-- creamos el evento
DROP EVENT IF EXISTS invoice_event

DELIMITER //
CREATE EVENT invoice_event ON SCHEDULE EVERY 1 MONTH
STARTS "2021-07-01 00:00:00" DO
BEGIN 
	CALL create_all_invoices();
END;

DELIMITER ;

SHOW EVENTS
