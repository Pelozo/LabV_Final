El portal de usuarios y aplicacion Android deberá permitir:

| |Http Method|Endpoint|File|Payload requried example|Requires login
|--|--|--|---|--|--|
Login de clientes |POST| /login/ | [UserController:50](https://github.com/Pelozo/LabV_Final/blob/main/src/main/java/net/pelozo/FinalTPLab5DB2/controller/UserController.java#L50)|```{"username": "usr","password": "pass"}```|
Consulta de facturas por rango de fechas.| GET| /clients/{id}/invoices?startDate=01-2020&endDate=10-2222|[ClientController:90](https://github.com/Pelozo/LabV_Final/blob/main/src/main/java/net/pelozo/FinalTPLab5DB2/controller/ClientController.java#L90)||✔
Consulta de deuda (Facturas impagas)| GET| /clients/{id}/invoices/unpaid |[ClientController:101](https://github.com/Pelozo/LabV_Final/blob/main/src/main/java/net/pelozo/FinalTPLab5DB2/controller/ClientController.java#L101)||✔
Consulta de consumo por rango de fechas| GET| /clients/{id}/intake?from=2020-01-01 01:01:01&to=2022-01-01 01:01:01|[ClientController:111](https://github.com/Pelozo/LabV_Final/blob/main/src/main/java/net/pelozo/FinalTPLab5DB2/controller/ClientController.java#L111)||✔
Consulta de mediciones por rango de fechas|GET| /clients/1/measurements?from=2020-01-01 01:01:01&to=2021-01-01 01:01:01|[ClientController:123](https://github.com/Pelozo/LabV_Final/blob/main/src/main/java/net/pelozo/FinalTPLab5DB2/controller/ClientController.java#L123)||✔


Desde el sistema de Backoffice, se debe permitir:

| |Http Method|Endpoint|File|Payload required example|Requires login
|--|--|--|---|--|--|
Login de empleados.| POST|/backoffice/login|[UserController:60](https://github.com/Pelozo/LabV_Final/blob/main/src/main/java/net/pelozo/FinalTPLab5DB2/controller/UserController.java#L60)|```{"username": "usr","password": "pass"}```|
Alta de tarifas.|POST|/tariff|[TariffController:43](https://github.com/Pelozo/LabV_Final/blob/main/src/main/java/net/pelozo/FinalTPLab5DB2/controller/TariffController.java#L43)|```{"name": "basic","value": 8}```|✔
Baja de tarifas.|DELETE|/tariff/{id]|[TariffController:61](https://github.com/Pelozo/LabV_Final/blob/main/src/main/java/net/pelozo/FinalTPLab5DB2/controller/TariffController.java#L61)||✔
Modificación de tarifas.|PUT|/tariff/{id]|[TariffController:70](https://github.com/Pelozo/LabV_Final/blob/main/src/main/java/net/pelozo/FinalTPLab5DB2/controller/TariffController.java#L70)|```{"name": "basic","value": 10}```|✔
Alta de domicilios|POST|/clients/{idClient}/residences|[ClientController:154](https://github.com/Pelozo/LabV_Final/blob/main/src/main/java/net/pelozo/FinalTPLab5DB2/controller/ClientController.java#L154)|```{"tariff": {"id": 1},"meter": {"id": 13},"address": {"street": "una calle","number":754}}```|✔
Baja de domicilios|DELETE|/residences/{idResidence}|[ResidenceController:57](https://github.com/Pelozo/LabV_Final/blob/main/src/main/java/net/pelozo/FinalTPLab5DB2/controller/ResidenceController.java#L57)||✔
Modificación de domicilios|PUT|/residences/{id}|[ResidenceController:64](https://github.com/Pelozo/LabV_Final/blob/main/src/main/java/net/pelozo/FinalTPLab5DB2/controller/ResidenceController.java#L64)|```{"tariff": {"id": 1},"meter": {"id": 1},"address": {"street": "una calle más mejor","number":20}}```|✔
Alta de medidores|POST|/meters|[MeterController:43](https://github.com/Pelozo/LabV_Final/blob/main/src/main/java/net/pelozo/FinalTPLab5DB2/controller/MeterController.java#L43)|```{"model": {"id": 1},"serialNumber": "5646","password": "kappa"}```|✔
Baja de medidores|DELETE|/meters/{id}|[MeterController:62](https://github.com/Pelozo/LabV_Final/blob/dev/src/main/java/net/pelozo/FinalTPLab5DB2/controller/MeterController.java#L62)||✔
Modificación|PUT|/meters/{id}|[MeterController:70](https://github.com/Pelozo/LabV_Final/blob/dev/src/main/java/net/pelozo/FinalTPLab5DB2/controller/MeterController.java#L70)|```{"model": {"id": 1},"serialNumber": "5646","password": "kappa"}```|✔
Consulta de facturas impagas por cliente y domicilio|GET|/clients/{clientId}/residences/{residenceId}/invoices/unpaid|[ClientController:144](https://github.com/Pelozo/LabV_Final/blob/main/src/main/java/net/pelozo/FinalTPLab5DB2/controller/ClientController.java#L144)||✔
Consulta 10 clientes más consumidores en un rango de fechas|GET|/clients/topConsumers?from=2000-01-01&to=2222-01-01|[ClientController:132](https://github.com/Pelozo/LabV_Final/blob/main/src/main/java/net/pelozo/FinalTPLab5DB2/controller/ClientController.java#L132)||✔
Consulta de mediciones de un domicilio por rango de fechas|GET|measurements/residence/{residenceId}|[MeasurementController:62](https://github.com/Pelozo/LabV_Final/blob/main/src/main/java/net/pelozo/FinalTPLab5DB2/controller/MeasurementController.java#L62)||✔


1) Generar las estructuras necesarias para dar soporte a 4 sistemas diferentes: [users_database.sql](https://github.com/Pelozo/LabV_Final/blob/main/BaseDeDatos/users_database.sql)

2) La facturación se realizará por un proceso automático en la base de datos. Se
debe programar este proceso para el primer día de cada mes y debe generar una
factura por medidor y debe tomar en cuenta todas las mediciones no facturadas
para cada uno de los medidores, sin tener en cuenta su fecha. La fecha de vencimiento de
esta factura será estipulado a 15 días: [invoices.sql](https://github.com/Pelozo/LabV_Final/blob/main/BaseDeDatos/invoices.sql)

3) Generar las estructuras necesarias para el cálculo de precio de cada medición y las
inserción de la misma. Se debe tener en cuenta que una modificación en la tarifa debe
modificar el precio de cada una de estas mediciones en la base de datos y generar una
factura de ajuste a la nueva medición de cada una de las mediciones involucradas con esta
tarifa:[tbi_measurement.sql](https://github.com/Pelozo/LabV_Final/blob/main/BaseDeDatos/tbi_measurement.sql) y [tau_update_tariffs.sql](https://github.com/Pelozo/LabV_Final/blob/main/BaseDeDatos/tau_update_tariffs.sql)

4) Generar las estructuras necesarias para dar soporte a las consultas de mediciones
por fecha y por usuario , debido a que tenemos restricción de que estas no pueden demorar
más de dos segundos y tenemos previsto que tendremos 500.000.000 de mediciones en el
sistema en el mediano plazo: [indexes.sql](https://github.com/Pelozo/LabV_Final/blob/main/BaseDeDatos/indexes.sql)

5. Como PLAN B , generar una estructura de base de datos NoSQL de su preferencia
para dar soporte al problema planteado:
[nosql](https://github.com/Pelozo/LabV_Final/blob/main/BaseDeDatos/noSQL)
