DELIMITER $$

-- Failure of a trigger causes the statement to fail, so trigger failure also causes rollback.

-- Sacado de:
-- https://dev.mysql.com/doc/refman/5.7/en/trigger-syntax.html

DROP PROCEDURE IF EXISTS invoice_adjustment

CREATE PROCEDURE invoice_adjustment(p_tariff_id INT)
BEGIN


	DECLARE v_residence_id INT;
	DECLARE v_first_r, v_last_r, v_total, v_new_t_amount, v_result FLOAT;
	DECLARE v_handler INT DEFAULT 0;
	

	-- declaramos un cursor el cual contiene el id de la residencia, la primera y la ultima medicion
	DECLARE cur_invoices CURSOR FOR SELECT r.id,MIN(m.kwh_value),MAX(kwh_value)
	FROM measurements m
	JOIN residences r
	ON r.id = m.residence_id
	LEFT JOIN tariffs t
	ON r.tariff_id = t.id
	WHERE t.id = p_tariff_id 
	GROUP BY r.id;
	-- declaramos una variable para verificar cuando la lectura del registro sea 0
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_handler = 1;
	
	-- conseguimos el nuevo valor de la tarifa
	 SELECT amount INTO v_new_t_amount
	 FROM tariffs 
	 WHERE id = p_tariff_id;
	
	-- abrimos el cursor
	OPEN cur_invoices;
	
	-- creamos un loop
	c_invoices:
	LOOP
	
	-- lo que trajo el cursor lo guardamos en las variables
	FETCH cur_invoices INTO v_residence_id,v_first_r, v_last_r;
	-- verificamos que el cursor trajo algo
	IF v_handler = 1 THEN
	-- si no trajo nada salimos del loop
		LEAVE c_invoices;
	END IF;
	
	-- conseguimos la suma de todas las invoices por cada residencia
	SELECT SUM(total) INTO v_total
	FROM invoices
	WHERE `residence_id` = v_residence_id
	GROUP BY residence_id;
	
	-- hacemos la diferencia entre las mediciones
	SET v_result = v_last_r - v_first_r;
	
	--  lo multiplicamos por la nueva tarifa y con eso sacamos la diferencia con el total de las facturas anteriores.
	SET v_total = (v_result * v_new_t_amount) - v_total; 
	
	-- hacemos la factura de ajuste con los nuevos valores
	INSERT INTO invoices(`residence_id`,`is_paid`,`is_due`,`due_date`,`issue_date`,`kwh_measurement`,`first_reading`,`last_reading`,`total`)
	VALUES (v_residence_id,FALSE,FALSE,DATE_ADD(DATE(NOW()),INTERVAL 30 DAY),DATE(NOW()),v_result,v_first_r, v_last_r, v_total);
	
	-- terminamos el loop
	END LOOP c_invoices;
	
	-- cerramos el cursor para liberar los registros tomados
	CLOSE cur_invoices;
	

END$$

DELIMITER ;
