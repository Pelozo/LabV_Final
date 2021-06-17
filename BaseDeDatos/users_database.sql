# a) BACKOFFICE, que permitirá el manejo de clientes, medidores y tarifas.

CREATE USER 'backoffice'@'localhost' IDENTIFIED BY '1234';
GRANT SELECT, INSERT, UPDATE, DELETE ON clients TO 'backoffice'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON meters TO 'backoffice'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON tariffs TO 'backoffice'@'localhost';


# b) CLIENTES, que permitirá consultas de mediciones y facturación.

CREATE USER 'clients'@'localhost' IDENTIFIED BY '1234';
GRANT SELECT ON measurements TO 'clients'@'localhost';
GRANT SELECT ON invoices TO 'clients'@'localhost';

# c) MEDIDORES, que será el sistema que enviará la información de mediciones a la base de datos.

CREATE USER 'meters'@'localhost' IDENTIFIED BY '1234';
GRANT INSERT ON measurements TO 'meters'@'localhost';

# d) FACTURACIÓN, proceso automático de facturación

CREATE USER 'invoices'@'localhost' IDENTIFIED BY '1234';
GRANT SELECT, UPDATE ON measurements TO 'invoices'@'localhost';
GRANT SELECT, INSERT ON invoices TO 'invoices'@'localhost';