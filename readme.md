# LifeBank Project
Los proyectos utilizan lombok, por lo que se recomienda instalar la herramienta de [este link](https://projectlombok.org/download).
 
Los servicios utilizan base de datos postgresql. 
Correr el script que est� en la ra�z de la carpeta con mi nombre y cambiar en los yml de los proyectos el puerto al correspondiente de su servicio, al igual que el usuario y contrase�a generica. El puerto generalmente es 5432, yo lo cambi� ya que tuve problemas con la instancia principal.

### Servicios
* **bnk-authentication-svc**: Servicio para autenticar y autorizar una petición. Sirve como gateway para los demás servicios. Toda petición de la web vendrá a este servicio.
* **bnk-product-svc**: Servicio que contiene la información de todos los productos (cuentas bancarias, tarjetas de crédito y préstamos).
* **bnk-transaction-svc**: Servicio que almacena las transacciones.
* **bnk-beneficiary-svc**: Servicio que maneja la información de los beneficiarios.

### Endpoints con datos de prueba
* **POST**: localhost:1999/login
⋅⋅⋅*Request*
```json
{
	"username": "LPMENDEZ",
	"password": "12345"
}
```

Todos los endpoints descritos abajo necesitan de un token de authentication en los parámetros del header request.
Este token que lo provee el inicio de sesión (arriba mencionado).

Para las pruebas iniciar sesión con usuario *LPMENDEZ* y contraseña *12345*

* **GET**: localhost:1999/product/getAccounts
* **GET**: localhost:1999/transactions/getTransactions/{accountID}?start=01-Jan-2019&end=30-Mar-2019&prd=ACC
⋅⋅⋅*Parámetos Path*
    * **accountID**: Id de la cuenta o producto. Prueba: 0000001
⋅⋅⋅*Parámetros Querystring*
    * **start**: Fecha de inicio en formato *dd-MMM-yyyy (Inglés)*
    * **end**: Fecha fin en formato *dd-MMM-yyyy (Inglés)*
    * **prd**: Tipo de producto. *ACC, LOAN, CARD.*
* **POST**: localhost:1999/operations/ownTransfer
⋅⋅⋅*Request*
```json
{
	"from": "0000002",
	"to": "0000001",
	"amount": 3.4,
	"description": "Prueba"
}
```
* **POST**: localhost:1999/operations/ownPayLoan
⋅⋅⋅*Request*
```json
{
	"from": "0000002",
	"to": "0000003",
	"amount": 50,
	"description": ""
}
```
* **POST**: localhost:1999/operations/thirdPayCard
⋅⋅⋅*Request*
```json
{
	"from": "0000002",
	"beneficiaryID": "3",
	"amount": 10,
	"description": "Pago de tarjeta abril"
}
```
* **POST**: localhost:1999/operations/thirdTransfer
⋅⋅⋅*Request*
```json
{
	"from": "0000001",
	"beneficiaryID": "1",
	"amount": 5.65,
	"description": "Almuerzo lunes"
}
```

Los siguientes endpoints iniciar sesión con usuario *PRUEBA* y contraseña *12345* y utilizar ese token en el header request

* **POST**: localhost:1999/operations/ownPayCard
⋅⋅⋅*Request*
```json
{
	"from": "0000002",
	"to": "0000003",
	"amount": 50,
	"description": ""
}
```
* **POST**: localhost:1999/operations/thirdPayLoan
⋅⋅⋅*Request*
```json
{
    "from": "0000004",
    "beneficiaryID": "2",
    "amount": 90,
    "description": ""
}
```
* **POST**: localhost:1999/beneficiaries/beneficiary
⋅⋅⋅*Request*
```json
{
	"name": "Ledys cuenta",
	"account": "0000002",
	"type": "ACC",
	"email": "ledys@test.com"
}
```
* **PATCH**: localhost:1999/beneficiaries/beneficiary/{beneficiaryID}
⋅⋅⋅*Parámetos Path*
    * **beneficiaryID**: Id del beneficiario. Prueba: 4
⋅⋅⋅*Request*
{
	"email": "ledys.mendez@test.com"
}
* **DELETE**: localhost:1999/beneficiaries/beneficiary/{beneficiaryID}
⋅⋅⋅*Parámetos Path*
    * **beneficiaryID**: Id del beneficiario. Prueba: 4