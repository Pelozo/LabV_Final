-- indice por id de residencia
CREATE INDEX idx_measurements_residence
ON measurements (residence_id) 
USING HASH;


-- indice por fecha de la medicion
CREATE INDEX idx_measurements_date
ON measurements (`date`)
USING BTREE;


-- esto demuestra que el indice existe y puede ser utilizado
EXPLAIN SELECT * FROM measurements WHERE `date` BETWEEN "2021-06-17 16:32:08" AND "2021-06-17 16:35:08" AND residence_id = 1;

-- probando queries para ver si los indices funcionan
EXPLAIN SELECT * 
FROM measurements m
JOIN residences r 
ON r.id = m.residence_id
JOIN clients c
ON c.id = r.client_id
WHERE c.id = 1 AND m.date BETWEEN "2021-06-17 16:32:08" AND "2021-07-23 16:32:08"

-- verificamos que los indices existan
SHOW INDEX FROM measurements


-- devuelve las mediciones por usuario y fecha
DROP PROCEDURE IF EXISTS stp_get_measurements_by_client_between_dates
DELIMITER //
CREATE PROCEDURE stp_get_measurements_by_client_between_dates(p_client_id INT, p_from DATETIME, p_to DATETIME)
BEGIN

-- conseguimos todos los datos necesarios 
SELECT c.*,m.*,mt.*
FROM measurements m
JOIN residences r
ON r.id = m.residence_id
JOIN clients c
ON r.client_id = c.id
JOIN meters mt
ON mt.id = r.meter_id
WHERE c.id = p_client_id AND m.date BETWEEN p_from AND p_to ;

END;

DELIMITER ;

CALL stp_get_measurements_by_client_between_dates(1,"2020-06-17 16:32:08","2022-06-17 08:44:26");
