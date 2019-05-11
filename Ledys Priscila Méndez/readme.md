# LifeBank Project
Los proyectos utilizan lombok, por lo que se recomienda instalar la herramienta de [este link](https://projectlombok.org/download).
 
Los servicios utilizan base de datos postgresql. 
Correr el script que está en la raíz de la carpeta con mi nombre y cambiar en los yml de los proyectos el puerto al correspondiente de su servicio, al igual que el usuario y contraseña generica. El puerto generalmente es 5432, yo lo cambié ya que tuve problemas con la instancia principal.

### Servicios
* **bnk-authentication-svc**: Servicio para autenticar y autorizar una petición. Sirve como gateway para los demás servicios. Toda petición de la web vendrá a este servicio.
* **bnk-product-svc**: Servicio que contiene la información de todos los productos (cuentas bancarias, tarjetas de crédito y préstamos).
* **bnk-transaction-svc**: Servicio que almacena las transacciones.
* **bnk-beneficiary-svc**: Servicio que maneja la información de los beneficiarios.

### Endpoints con datos de prueba
* **POST**: localhost:1999/login
*Request*
{
	"username": "LPMENDEZ",
	"password": "12345"
}

Todos los endpoints descritos abajo necesitan de un token de authentication que lo provee el inicio de sesión (arriba mencionado).

* **GET**: localhost:1999/product/getAccounts
* **GET**: localhost:1999/transactions/getTransactions/{accountID}?start=01-Jan-2019&end=30-Mar-2019&prd=ACC
* *Parámetos Path*
    * **accountID**: Id de la cuenta o producto. Prueba: 0000001
* *Parámetros Querystring*
    * **start**: Fecha de inicio en formato *dd-MMM-yyyy (Inglés)*
    * **end**: Fecha fin en formato *dd-MMM-yyyy (Inglés)*
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
* *Parámetos Path*
    * **beneficiaryID**: Id del beneficiario. Prueba: 1
* *Request*
{
	"email": "prueba2@test.com"
}
* **DELETE**: localhost:1999/beneficiaries/beneficiary/{beneficiaryID}
*Parámetos Path*
    * **beneficiaryID**: Id del beneficiario. Prueba: 1