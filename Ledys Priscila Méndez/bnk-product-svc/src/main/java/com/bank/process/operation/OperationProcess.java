package com.bank.process.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bank.data.IProductData;
import com.bank.data.beneficiary.IBeneficiaryData;
import com.bank.data.transaction.ITransactionData;
import com.bank.exception.ApplicationException;
import com.bank.pojo.input.operations.OperationReq;
import com.bank.pojo.output.beneficiary.Beneficiary;
import com.bank.pojo.output.detail.AccountDetail;
import com.bank.pojo.output.detail.CardDetail;
import com.bank.pojo.output.detail.LoanDetail;
import com.bank.pojo.output.transaction.Transaction;
import com.bank.utils.ResponseCode;
import com.bank.utils.ResponseMsg;

@Service
public class OperationProcess implements IOperationProcess {

	private IProductData<LoanDetail> loan;
	private IProductData<CardDetail> card;
	private IProductData<AccountDetail> acc;
	private ITransactionData tran;
	private IBeneficiaryData ben;
	private Logger log;
	
	public OperationProcess(@Qualifier("BeanLoanData") IProductData<LoanDetail> loan,
			@Qualifier("BeanCardData") IProductData<CardDetail> card,
			@Qualifier("BeanAccData") IProductData<AccountDetail> acc,
			@Qualifier("BeanTraData") ITransactionData tran,
			@Qualifier("BeanBenData") IBeneficiaryData ben) {
		this.loan = loan;
		this.card = card;
		this.acc = acc;
		this.ben = ben;
		this.tran = tran;
		this.log = LoggerFactory.getLogger(getClass());
	}
	
	@Override
	public Transaction ownTransfer(OperationReq req) {
		try {
			// validar que las cuentas pertenezcan al cliente y que existan por supuesto y sean de tipo cuenta
			AccountDetail from =  acc.retrieveByUsrAndId(req.getUser(), req.getFrom());
			AccountDetail to =  acc.retrieveByUsrAndId(req.getUser(), req.getTo());
			if(from == null || to == null) {
				//devolver codigo 404
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.INVALID)),
						ResponseCode.INVALID, ResponseMsg.INVALID);
			}
			//ahora validar que tenga el saldo suficiente.. para la transacción.
			if(from.getBalance() < req.getAmount()) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.NO_BALANCE)),
						ResponseCode.NO_BALANCE, ResponseMsg.NO_BALANCE);
			}
			//realizar descuentos. 
			// Se resta de cuenta from
			if(!acc.operation(req.getFrom(), req.getAmount()  * -1)) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
						ResponseCode.GENERAL, "Operation error in FROM account");
			}
			// y aumentar a la to
			if(!acc.operation(req.getTo(), req.getAmount())) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
						ResponseCode.GENERAL, "Operation error in TO account");
			}
			//guardar las transacciones..

			Transaction input = new Transaction();
			input.setAccId(req.getTo());
			input.setAmount(req.getAmount());
			input.setDescription("Abono por transferencia de "+req.getFrom()+". "+req.getDescription());
			input.setUsrCode(req.getUser());
			if(tran.save(input) == null) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
						ResponseCode.GENERAL, ResponseMsg.GENERAL);
			}
			
			input = new Transaction();
			input.setAccId(req.getFrom());
			input.setAmount(req.getAmount() * -1);
			input.setDescription("Transferencia hacia "+req.getTo()+". "+req.getDescription());
			input.setUsrCode(req.getUser());
			Transaction response = tran.save(input);

			if(response == null) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
						ResponseCode.GENERAL, ResponseMsg.GENERAL);
			}
			
			return response;
			
		}
		catch(ApplicationException ex) {
			throw ex;
		}
		catch(Exception ex) {
			log.error("Error in own transferences. Error: {}. User: {}", ex.getMessage(), req.getUser(), ex);
		}
		throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
				ResponseCode.GENERAL, ResponseMsg.GENERAL);
	}

	@Override
	public Transaction cardPayment(OperationReq req) {
		try {
			// validar que las cuentas pertenezcan al cliente
			AccountDetail from =  acc.retrieveByUsrAndId(req.getUser(), req.getFrom());
			CardDetail to =  card.retrieveByUsrAndId(req.getUser(), req.getTo());
			if(from == null || to == null) {
				//devolver codigo 404
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.INVALID)),
						ResponseCode.INVALID, ResponseMsg.INVALID);
			}
			//ahora validar que tenga el saldo suficiente.. para la transacción.
			if(from.getBalance() < req.getAmount()) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.NO_BALANCE)),
						ResponseCode.NO_BALANCE, ResponseMsg.NO_BALANCE);
			}
			//realizar descuentos. 
			// Se resta de cuenta from
			if(!acc.operation(req.getFrom(), req.getAmount()  * -1)) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
						ResponseCode.GENERAL, "Operation error in FROM account");
			}
			// y sumar el available de la tarjeta..
			if(!card.operation(req.getTo(), req.getAmount())) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
						ResponseCode.GENERAL, "Operation error in CARD account");
			}
			//guardar las transacciones..

			Transaction input = new Transaction();
			input.setAccId(req.getTo());
			input.setAmount(req.getAmount());
			input.setDescription("Abono a pago de tarjeta de "+req.getFrom()+". "+req.getDescription());
			input.setUsrCode(req.getUser());
			if(tran.save(input) == null) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
						ResponseCode.GENERAL, ResponseMsg.GENERAL);
			}
			
			input = new Transaction();
			input.setAccId(req.getFrom());
			input.setAmount(req.getAmount() * -1);
			input.setDescription("Pago de tarjeta No."+req.getTo()+". "+req.getDescription());
			input.setUsrCode(req.getUser());
			Transaction response = tran.save(input);

			if(response == null) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
						ResponseCode.GENERAL, ResponseMsg.GENERAL);
			}
			
			return response;
			
		}
		catch(ApplicationException ex) {
			throw ex;
		}
		catch(Exception ex) {
			log.error("Error in payment credit card. Error: {}. User: {}", ex.getMessage(), req.getUser(), ex);
		}
		throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
				ResponseCode.GENERAL, ResponseMsg.GENERAL);
	}

	@Override
	public Transaction loanPayment(OperationReq req) {
		try {
			// validar que las cuentas pertenezcan al cliente
			AccountDetail from =  acc.retrieveByUsrAndId(req.getUser(), req.getFrom());
			LoanDetail to =  loan.retrieveByUsrAndId(req.getUser(), req.getTo());
			if(from == null || to == null) {
				//devolver codigo 404
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.INVALID)),
						ResponseCode.INVALID, ResponseMsg.INVALID);
			}
			//ahora validar que tenga el saldo suficiente.. para la transacción.
			if(from.getBalance() < req.getAmount()) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.NO_BALANCE)),
						ResponseCode.NO_BALANCE, ResponseMsg.NO_BALANCE);
			}
			//realizar descuentos. 
			// Se resta de cuenta from
			if(!acc.operation(req.getFrom(), req.getAmount()  * -1)) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
						ResponseCode.GENERAL, "Operation error in FROM account");
			}
			// y restar el debt del prestamo..
			if(!loan.operation(req.getTo(), req.getAmount() * -1)) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
						ResponseCode.GENERAL, "Operation error in LOAN account");
			}
			//guardar las transacciones..

			Transaction input = new Transaction();
			input.setAccId(req.getTo());
			input.setAmount(req.getAmount());
			input.setDescription("Abono a pago de préstamo de "+req.getFrom()+". "+req.getDescription());
			input.setUsrCode(req.getUser());
			if(tran.save(input) == null) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
						ResponseCode.GENERAL, ResponseMsg.GENERAL);
			}
			
			input = new Transaction();
			input.setAccId(req.getFrom());
			input.setAmount(req.getAmount() * -1);
			input.setDescription("Pago de préstamo No."+req.getTo()+". "+req.getDescription());
			input.setUsrCode(req.getUser());
			Transaction response = tran.save(input);

			if(response == null) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
						ResponseCode.GENERAL, ResponseMsg.GENERAL);
			}
			
			return response;
			
		}
		catch(ApplicationException ex) {
			throw ex;
		}
		catch(Exception ex) {
			log.error("Error in payment credit card. Error: {}. User: {}", ex.getMessage(), req.getUser(), ex);
		}
		throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
				ResponseCode.GENERAL, ResponseMsg.GENERAL);
	}

	@Override
	public Transaction thirdTransfer(OperationReq req) {
		try {

			//ahora validar que el beneficiario pertenezca al cliente
			Beneficiary beneficiary = ben.findByIdAndUsr(req.getBeneficiaryID(), req.getUser());
			if(beneficiary == null) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.ERROR)),
						ResponseCode.ERROR, "Beneficiary not exists");
			}
			req.setTo(beneficiary.getAccount());
			
			// validar que la cuenta pertenezcan al cliente
			AccountDetail from =  acc.retrieveByUsrAndId(req.getUser(), req.getFrom());
			//validar que sea una cuenta bancaria
			AccountDetail to =  acc.retrieveById(req.getTo());
			
			if(from == null || to == null) {
				//devolver codigo 404
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.INVALID)),
						ResponseCode.INVALID, ResponseMsg.INVALID);
			}
			
			if(from.getBalance() < req.getAmount()) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.NO_BALANCE)),
						ResponseCode.NO_BALANCE, ResponseMsg.NO_BALANCE);
			}
			//realizar descuentos. 
			// Se resta de cuenta from
			if(!acc.operation(req.getFrom(), req.getAmount()  * -1)) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
						ResponseCode.GENERAL, "Operation error in FROM account");
			}
			// y sumar al to
			if(!acc.operation(req.getTo(), req.getAmount())) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
						ResponseCode.GENERAL, "Operation error in TO third account");
			}
			//guardar las transacciones..

			Transaction input = new Transaction();
			input.setAccId(req.getTo());
			input.setAmount(req.getAmount());
			input.setDescription("Abono por transferencia de "+req.getFrom()+". "+req.getDescription());
			input.setUsrCode(req.getUser());
			if(tran.save(input) == null) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
						ResponseCode.GENERAL, ResponseMsg.GENERAL);
			}
			
			input = new Transaction();
			input.setAccId(req.getFrom());
			input.setAmount(req.getAmount() * -1);
			input.setDescription("Transferencia hacia No. "+req.getTo()+". "+req.getDescription());
			input.setUsrCode(req.getUser());
			Transaction response = tran.save(input);

			if(response == null) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
						ResponseCode.GENERAL, ResponseMsg.GENERAL);
			}
			
			return response;
			
		}
		catch(ApplicationException ex) {
			throw ex;
		}
		catch(Exception ex) {
			log.error("Error in transfer third account. Error: {}. User: {}", ex.getMessage(), req.getUser(), ex);
		}
		throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
				ResponseCode.GENERAL, ResponseMsg.GENERAL);
	}

	@Override
	public Transaction thirdCardPay(OperationReq req) {
		try {

			//ahora validar que el beneficiario pertenezca al cliente
			Beneficiary beneficiary = ben.findByIdAndUsr(req.getBeneficiaryID(), req.getUser());
			if(beneficiary == null) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.ERROR)),
						ResponseCode.ERROR, "Beneficiary not exists");
			}
			req.setTo(beneficiary.getAccount());
			
			// validar que la cuenta pertenezcan al cliente
			AccountDetail from =  acc.retrieveByUsrAndId(req.getUser(), req.getFrom());
			//validar que sea una tarjeta de credito
			CardDetail to =  card.retrieveById(req.getTo());
			
			if(from == null || to == null) {
				//devolver codigo 404
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.INVALID)),
						ResponseCode.INVALID, ResponseMsg.INVALID);
			}
			
			if(from.getBalance() < req.getAmount()) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.NO_BALANCE)),
						ResponseCode.NO_BALANCE, ResponseMsg.NO_BALANCE);
			}
			//realizar descuentos. 
			// Se resta de cuenta from
			if(!acc.operation(req.getFrom(), req.getAmount()  * -1)) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
						ResponseCode.GENERAL, "Operation error in FROM account");
			}
			// y sumar al to
			if(!card.operation(req.getTo(), req.getAmount())) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
						ResponseCode.GENERAL, "Operation error in CARD third account");
			}
			//guardar las transacciones..

			Transaction input = new Transaction();
			input.setAccId(req.getTo());
			input.setAmount(req.getAmount());
			input.setDescription("Abono a pago de tarjeta de Crédito desde No. "+req.getFrom()+". "+req.getDescription());
			input.setUsrCode(req.getUser());
			if(tran.save(input) == null) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
						ResponseCode.GENERAL, ResponseMsg.GENERAL);
			}
			
			input = new Transaction();
			input.setAccId(req.getFrom());
			input.setAmount(req.getAmount() * -1);
			input.setDescription("Pago de tarjeta de crédito a No. "+req.getTo()+". "+req.getDescription());
			input.setUsrCode(req.getUser());
			Transaction response = tran.save(input);

			if(response == null) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
						ResponseCode.GENERAL, ResponseMsg.GENERAL);
			}
			
			return response;
			
		}
		catch(ApplicationException ex) {
			throw ex;
		}
		catch(Exception ex) {
			log.error("Error in transfer third account. Error: {}. User: {}", ex.getMessage(), req.getUser(), ex);
		}
		throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
				ResponseCode.GENERAL, ResponseMsg.GENERAL);
	}

	@Override
	public Transaction thirdLoanPay(OperationReq req) {
		try {

			//ahora validar que el beneficiario pertenezca al cliente
			Beneficiary beneficiary = ben.findByIdAndUsr(req.getBeneficiaryID(), req.getUser());
			if(beneficiary == null) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.ERROR)),
						ResponseCode.ERROR, "Beneficiary not exists");
			}
			req.setTo(beneficiary.getAccount());
			
			// validar que la cuenta pertenezcan al cliente
			AccountDetail from =  acc.retrieveByUsrAndId(req.getUser(), req.getFrom());
			//validar que sea un prestamo
			LoanDetail to =  loan.retrieveById(req.getTo());
			
			if(from == null || to == null) {
				//devolver codigo 404
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.INVALID)),
						ResponseCode.INVALID, ResponseMsg.INVALID);
			}
			
			if(from.getBalance() < req.getAmount()) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.NO_BALANCE)),
						ResponseCode.NO_BALANCE, ResponseMsg.NO_BALANCE);
			}
			//realizar descuentos. 
			// Se resta de cuenta from
			if(!acc.operation(req.getFrom(), req.getAmount()  * -1)) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
						ResponseCode.GENERAL, "Operation error in FROM account");
			}
			// y restar al prestamo de tercero
			if(!loan.operation(req.getTo(), req.getAmount() * -1 )) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
						ResponseCode.GENERAL, "Operation error in LOAN third account");
			}
			//guardar las transacciones..

			Transaction input = new Transaction();
			input.setAccId(req.getTo());
			input.setAmount(req.getAmount());
			input.setDescription("Abono a pago de préstamo desde "+req.getFrom()+". "+req.getDescription());
			input.setUsrCode(req.getUser());
			if(tran.save(input) == null) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
						ResponseCode.GENERAL, ResponseMsg.GENERAL);
			}
			
			input = new Transaction();
			input.setAccId(req.getFrom());
			input.setAmount(req.getAmount() * -1);
			input.setDescription("Pago de préstamo hacia No. "+req.getTo()+". "+req.getDescription());
			input.setUsrCode(req.getUser());
			Transaction response = tran.save(input);

			if(response == null) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
						ResponseCode.GENERAL, ResponseMsg.GENERAL);
			}
			
			return response;
			
		}
		catch(ApplicationException ex) {
			throw ex;
		}
		catch(Exception ex) {
			log.error("Error in transfer third account. Error: {}. User: {}", ex.getMessage(), req.getUser(), ex);
		}
		throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.GENERAL)),
				ResponseCode.GENERAL, ResponseMsg.GENERAL);
	}


}
