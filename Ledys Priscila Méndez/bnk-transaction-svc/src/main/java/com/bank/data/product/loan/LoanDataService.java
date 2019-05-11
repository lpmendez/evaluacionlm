package com.bank.data.product.loan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.bank.data.product.IProductData;
import com.bank.pojo.product.LoanDetail;
import com.bank.utils.RestAPI;

@Service("LoanDataService")
public class LoanDataService implements IProductData<LoanDetail> {

	private RestAPI rest;
	private Environment env;
	private Logger log;

	
	public LoanDataService(RestAPI rest,Environment env){
		this.rest = rest;
		this.env = env;
		this.log = LoggerFactory.getLogger(getClass());		
	}	
	
	@Override
	public LoanDetail retrieveByUsrAndId(String usr, String id) {
		
		try {
			ParameterizedTypeReference<LoanDetail> ptr = new ParameterizedTypeReference<LoanDetail>(){};
			return rest.call(
					env.getProperty("config.endpoints.svc.product-svc.loans"), 
					HttpMethod.GET, null, ptr, usr, id);			
			
		} catch (Exception e) {
			log.error("Retrieve info loans Error method fail" + e.getMessage(), e);
		}
		return null;
	}

}
