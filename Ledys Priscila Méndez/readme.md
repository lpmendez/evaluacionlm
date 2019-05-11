# LifeBank Project
Los proyectos utilizan lombok, por lo que se recomienda instalar la herramienta de [este link](https://projectlombok.org/download).
 
Los servicios utilizan base de datos postgresql. 
Correr el script que est� en la ra�z de la carpeta con mi nombre y cambiar en los yml de los proyectos el puerto al correspondiente de su servicio, al igual que el usuario y contrase�a generica. El puerto generalmente es 5432, yo lo cambi� ya que tuve problemas con la instancia principal.

### Servicios
* **bnk-authentication-svc**: Servicio para autenticar y autorizar una petici�n. Sirve como gateway para los dem�s servicios. Toda petici�n de la web vendr� a este servicio.
* **bnk-product-svc**: Servicio que contiene la informaci�n de todos los productos (cuentas bancarias, tarjetas de cr�dito y pr�stamos).
* **bnk-transaction-svc**: Servicio que almacena las transacciones.
* **bnk-beneficiary-svc**: Servicio que maneja la informaci�n de los beneficiarios.

### Endpoints con datos de prueba
* **POST**: localhost:1999/login
*Request*
{
	"username": "LPMENDEZ",
	"password": "12345"
}

Todos los endpoints descritos abajo necesitan de un token de authentication que lo provee el inicio de sesi�n (arriba mencionado).

* **GET**: localhost:1999/product/getAccounts
* **GET**: localhost:1999/transactions/getTransactions/{accountID}?start=01-Jan-2019&end=30-Mar-2019&prd=ACC
* *Par�metos Path*
    * **accountID**: Id de la cuenta o producto. Prueba: 0000001
* *Par�metros Querystring*
    * **start**: Fecha de inicio en formato *dd-MMM-yyyy (Ingl�s)*
    * **end**: Fecha fin en formato *dd-MMM-yyyy (Ingl�s)*
    * **prd**: Tipo de producto. *ACC, LOAN, CARD.*
* **POST**: localhost:1999/beneficiaries/beneficiary
*Request*
{
	"name": "Prueba",
	"account": "0000004",
	"type": "ACC",
	"email": "prueba@test.com"
}
* **PATCH**: localhost:1999/beneficiaries/beneficiary/{beneficiaryID}
* *Par�metos Path*
    * **beneficiaryID**: Id del beneficiario. Prueba: 1
* *Request*
{
	"email": "prueba2@test.com"
}
* **DELETE**: localhost:1999/beneficiaries/beneficiary/{beneficiaryID}
*Par�metos Path*
    * **beneficiaryID**: Id del beneficiario. Prueba: 1