DELIMITER $$


DROP TRIGGER IF EXISTS tau_update_tariffs

CREATE TRIGGER tau_update_tariffs AFTER UPDATE ON tariffs
-- ponemso for each row para que si 5 tarifas se modifican en un solo statement este trigger se llama 5 veces
    FOR EACH ROW BEGIN
	
	-- preguntamos si cambio el valor de la tarifa
	IF old.amount <> new.amount THEN
	-- actualizamos el precio de todas las medicion que tenga esa tarifa
		UPDATE measurements m
		JOIN residences r
		ON r.id = m.residence_id
		SET m.kwh_price = m.kwh_value * new.amount
		WHERE r.tariff_id = old.id;
	
	-- llamamos al stored procedure para generar facturas de ajuste
		CALL invoice_adjustment(old.id);
	END IF;
	
END;
$$

DELIMITER ;