package com.bank.process.product;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bank.data.product.IProductData;
import com.bank.data.transaction.ITransactionData;
import com.bank.exception.ApplicationException;
import com.bank.pojo.input.ProcessReq;
import com.bank.pojo.output.Transaction;
import com.bank.pojo.output.TransactionDetail;
import com.bank.pojo.output.loan.Loan;
import com.bank.pojo.product.LoanDetail;
import com.bank.utils.ResponseCode;
import com.bank.utils.ResponseMsg;

@Service("loan")
public class LoanProcess implements IProductProcess {

	private Logger log;
	private IProductData<LoanDetail> prodData;
	private ITransactionData tranData;
	private SimpleDateFormat output;
	private SimpleDateFormat svc;
	
	public LoanProcess(@Qualifier("BeanLoanData") IProductData<LoanDetail> prodData,
			@Qualifier("BeanTranData") ITransactionData tranData,
			Environment env) {
		this.prodData = prodData;
		this.tranData = tranData;
		output = new SimpleDateFormat(env.getProperty("config.date.output"));
		svc = new SimpleDateFormat(env.getProperty("config.format"), Locale.ENGLISH);
		this.log = LoggerFactory.getLogger(getClass());
	}
	
	@Override
	public Transaction execute(ProcessReq input) {
		Loan response = new Loan();
		LoanDetail info= prodData.retrieveByUsrAndId(input.getUser(), input.getAccount());
		//validando si en verdad existe, sino 404
		if(info == null) {
			throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.INVALID)), 
					ResponseCode.INVALID, ResponseMsg.INVALID);
		}
		
		try {
			List<TransactionDetail> transactions = tranData.retrieveBy(
					input.getAccount(), input.getUser(), input.getStartDate(), input.getEndDate());
			//empieza la transformación
			response.setTransactions(transactions);
			
			//ahora la info...
			response.setDebt(info.getDebt());
			response.setStartDate(output.format(svc.parse(input.getStartDate())));
			response.setEndDate(output.format(svc.parse(input.getEndDate())));
			response.setId(info.getId());
			response.setInterestAmount(info.getInterestAmount());
			response.setInterestRate(info.getInterestRate());
			response.setTotal(info.getTotal());
			
			return response;
			
		}
		catch(Exception ex) {
			log.error("Error in loan process. Error: {}. User. {}", ex.getMessage(), input.getUser(), ex);
		}
		return null;
	}
	

}
