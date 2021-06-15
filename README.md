El portal de usuarios y aplicacion Android deberá permitir:

| |Http Method|Endpoint|File|Payload requried example|Requires login
|--|--|--|---|--|--|
Login de clientes |POST| /login/ | [UserController:50](https://github.com/Pelozo/LabV_Final/blob/main/src/main/java/net/pelozo/FinalTPLab5DB2/controller/UserController.java#L50)|{"username": "usr","password": "pass"}
Consulta de facturas por rango de fechas.| GET| /clients/{id}/invoices?startDate=01-2020&endDate=10-2222|[ClientController:90](https://github.com/Pelozo/LabV_Final/blob/main/src/main/java/net/pelozo/FinalTPLab5DB2/controller/ClientController.java#L90)||✔
Consulta de deuda (Facturas impagas)| GET| /clients/{id}/invoices/unpaid |[ClientController:101](https://github.com/Pelozo/LabV_Final/blob/main/src/main/java/net/pelozo/FinalTPLab5DB2/controller/ClientController.java#L101)||✔
Consulta de consumo por rango de fechas(el usuario va a ingresar un rango de fechas y quiere saber cuánto consumió en ese periodo en Kwh y dinero)| GET| /clients/{id}/intake?from=2020-01-01 01:01:01&to=2022-01-01 01:01:01|[ClientController:111](https://github.com/Pelozo/LabV_Final/blob/main/src/main/java/net/pelozo/FinalTPLab5DB2/controller/ClientController.java#L111)||✔
Consulta de mediciones por rango de fechas|GET| /clients/1/measurements?from=2020-01-01 01:01:01&to=2021-01-01 01:01:01|[ClientController:123](https://github.com/Pelozo/LabV_Final/blob/main/src/main/java/net/pelozo/FinalTPLab5DB2/controller/ClientController.java#L123)||✔


Desde el sistema de Backoffice, se debe permitir:

| |Http Method|Endpoint|File|Payload required example|Requires login
|--|--|--|---|--|--|
Login de empleados.| POST|/backoffice/login|[UserController:60](https://github.com/Pelozo/LabV_Final/blob/main/src/main/java/net/pelozo/FinalTPLab5DB2/controller/UserController.java#L60)|{"username": "usr","password": "pass"}|
Alta de tarifas.|POST|/tariff|[TariffController:43](https://github.com/Pelozo/LabV_Final/blob/main/src/main/java/net/pelozo/FinalTPLab5DB2/controller/TariffController.java#L43)|{"name": "basic","value": 8}|✔
Baja de tarifas.|DELETE|/tariff/{id]|[TariffController:61](https://github.com/Pelozo/LabV_Final/blob/main/src/main/java/net/pelozo/FinalTPLab5DB2/controller/TariffController.java#L61)||✔
Modificación de tarifas.|PUT|/tariff/{id]|[TariffController:70](https://github.com/Pelozo/LabV_Final/blob/main/src/main/java/net/pelozo/FinalTPLab5DB2/controller/TariffController.java#L70)|{"name": "basic","value": 10}|✔
Alta de domicilios|POST|/clients/{idClient}/residences|[ResidenceController:55](https://github.com/Pelozo/LabV_Final/blob/main/src/main/java/net/pelozo/FinalTPLab5DB2/controller/ResidenceController.java#L55)||


Alta, baja y modificación de **domicilios** y medidores.
Domicilio:

			Alta:
					POST /clients/{idClient}/residences
					
			Baja: 
					DELETE /clients/{idClient}/residences/{idResidence}
					
					Salvar:
							Tariff
							Client
					
					Matar 
							Meter	
							Address
							Measurements
							Invoice (tratar de salvar?)

			Modificación
			
					PUT /clients/{idClient}/residences/{idResidence}
					
					Client
					Tariff
					Address
					Meter
					
		Medidores:
		
			Alta: 
					POST /meters/					
			Baja:
					DELETE /meters/{id}

			Modificación:
					PUT /meters/{id}

4) Consulta de facturas impagas por cliente y domicilio.

       /clients/{clientId}/residences/{residenceId}/invoices/unpaid

5) Consulta 10 clientes más consumidores en un rango de fechas.

   	/clients/topConsumers?from=2000-01-01&to=2222-01-01


6) Consulta de mediciones de un domicilio por rango de fechas

   	measurements/residence/{residenceId}

