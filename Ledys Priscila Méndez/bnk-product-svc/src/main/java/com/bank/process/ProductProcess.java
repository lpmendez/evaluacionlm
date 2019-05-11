package com.bank.process;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bank.data.IProductData;
import com.bank.exception.ApplicationException;
import com.bank.parse.AccountParse;
import com.bank.parse.CardParse;
import com.bank.parse.IAllProductParse;
import com.bank.parse.LoanParse;
import com.bank.pojo.output.all.Product;
import com.bank.pojo.output.all.ProductRes;
import com.bank.pojo.output.detail.AccountDetail;
import com.bank.pojo.output.detail.CardDetail;
import com.bank.pojo.output.detail.LoanDetail;
import com.bank.utils.ResponseCode;
import com.bank.utils.ResponseMsg;

@Service
public class ProductProcess implements IProductProcess {

	private IProductData<LoanDetail> loan;
	private IProductData<CardDetail> card;
	private IProductData<AccountDetail> acc;
	private IAllProductParse<List<CardDetail>> cardParse;
	private IAllProductParse<List<LoanDetail>> loanParse;
	private IAllProductParse<List<AccountDetail>> accParse;
	private Logger log;
	
	public ProductProcess(@Qualifier("BeanLoanData") IProductData<LoanDetail> loan,
			@Qualifier("BeanCardData") IProductData<CardDetail> card,
			@Qualifier("BeanAccData") IProductData<AccountDetail> acc) {
		this.loan = loan;
		this.card = card;
		this.acc = acc;
		this.log = LoggerFactory.getLogger(getClass());
		cardParse = new CardParse(); 
		loanParse = new LoanParse(); 
		accParse = new AccountParse(); 
	}
	@Override
	public ProductRes getAllProducts(String user) {
		
		try {

			ProductRes response = new ProductRes();
			//aqui se definen todos los productos
			List<LoanDetail> loans = loan.retrieveAllByUser(user);

			List<CardDetail> cards = card.retrieveAllByUser(user);

			List<AccountDetail> accs = acc.retrieveAllByUser(user);
			
			Product accounts = new Product();
			accounts.setCreditCard(cardParse.parse(cards));
			accounts.setLoan(loanParse.parse(loans));
			accounts.setPersonal(accParse.parse(accs));
			
			response.setAccounts(accounts);
			
			return response;
		}
		catch(Exception ex) {
			log.error("Error in get all products. Error: {}. User: {}", ex.getMessage(), user, ex);
		}
		
		throw new ApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.ERROR, ResponseMsg.ERROR);
	}

}
