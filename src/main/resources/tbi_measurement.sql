DROP TRIGGER IF EXISTS tbi_measurement;

DELIMITER ##

CREATE TRIGGER tbi_measurement BEFORE INSERT
ON measurements
FOR EACH ROW

BEGIN

	DECLARE v_last_date DATETIME DEFAULT NULL;
	DECLARE v_last_measurement FLOAT DEFAULT 0;
	DECLARE v_meter_id INT DEFAULT 0;
	DECLARE v_tariff_amount FLOAT DEFAULT 0;
	
	/*conseguimos la tarifa de la residencia*/
	
	 SELECT t.amount INTO v_tariff_amount
		FROM tariffs t
		JOIN residences r
		ON t.id = r.tariff_id
		WHERE r.id = new.residence_id ; 
	
	/*conseguimos el id del meter*/
	
	SELECT meter_id INTO v_meter_id FROM residences WHERE id = new.residence_id;
	
	/*conseguimos la fecha de la ultima medicion del medidor */
	SELECT MAX(m.date) INTO v_last_date 
	FROM measurements m
	JOIN residences r
	ON m.residence_id = r.id
	WHERE r.meter_id = v_meter_id;
	
	IF(v_last_date IS NOT NULL) THEN
	
	/*traemos la ultima medicion del medidor*/
	
		SELECT kwh_value INTO v_last_measurement 
		FROM measurements m
		JOIN residences r
		ON m.residence_id = r.id
		WHERE r.meter_id = v_meter_id
		AND `date` = v_last_date
		ORDER BY m.id DESC
		LIMIT 1;
	
		SET new.kwh_price = (new.kwh_value - v_last_measurement) * v_tariff_amount;
	
	ELSE
		SET new.kwh_price = new.kwh_value * v_tariff_amount;
	END IF;

END;

DELIMITER;

SELECT *FROM measurements


SELECT MAX(`date`)
FROM measurements m
	JOIN residences r
	ON m.residence_id = r.id
	WHERE r.meter_id = 1;

SELECT M.*
FROM measurements M
WHERE M.residence_id = :residenceId AND DATE BETWEEN :FROM AND :TO

