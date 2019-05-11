package com.bank.data.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.bank.pojo.output.transaction.Transaction;
import com.bank.utils.RestAPI;

@Service("TransactionService")
public class TransactionService implements ITransactionData {

	private RestAPI rest;
	private Environment env;
	private Logger log;

	
	public TransactionService(RestAPI rest,Environment env){
		this.rest = rest;
		this.env = env;
		this.log = LoggerFactory.getLogger(getClass());		
	}	
	
	@Override
	public Transaction save(Transaction input) {

		try {
			ParameterizedTypeReference<Transaction> ptr = new ParameterizedTypeReference<Transaction>(){};
			return rest.call(
					env.getProperty("config.endpoints.svc.transaction-svc.save"), 
					HttpMethod.POST, input, ptr);			
			
		} catch (Exception e) {
			log.error("Save transaction Error method fail" + e.getMessage(), e);
		}
		return null;
	}

}
