CREATE INDEX idx_measurements_residence
ON measurements (residence_id) 
USING HASH;



CREATE INDEX idx_measurements_date
ON measurements (`date`)
USING BTREE;


-- esto demuestra que el indice existe y puede ser utilizado
EXPLAIN SELECT * FROM measurements WHERE `date` BETWEEN "2021-06-17 16:32:08" AND "2021-06-17 16:35:08" AND residence_id = 1;

EXPLAIN SELECT * 
FROM measurements m
JOIN residences r 
ON r.id = m.residence_id
JOIN clients c
ON c.id = r.client_id
WHERE c.id = 1 AND m.date BETWEEN "2021-06-17 16:32:08" AND "2021-07-23 16:32:08"