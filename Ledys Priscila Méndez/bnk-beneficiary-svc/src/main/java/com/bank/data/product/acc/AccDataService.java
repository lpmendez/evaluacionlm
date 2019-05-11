package com.bank.data.product.acc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.bank.data.product.IProductData;
import com.bank.pojo.product.AccountDetail;
import com.bank.utils.RestAPI;

@Service("AccDataService")
public class AccDataService implements IProductData<AccountDetail> {

	private RestAPI rest;
	private Environment env;
	private Logger log;

	
	public AccDataService(RestAPI rest,Environment env){
		this.rest = rest;
		this.env = env;
		this.log = LoggerFactory.getLogger(getClass());		
	}	
	
	@Override
	public AccountDetail retrieveByUsrAndId(String usr, String id) {
		
		try {
			ParameterizedTypeReference<AccountDetail> ptr = new ParameterizedTypeReference<AccountDetail>(){};
			return rest.call(
					env.getProperty("config.endpoints.svc.product-svc.accs"), 
					HttpMethod.GET, null, ptr, usr, id);			
			
		} catch (Exception e) {
			log.error("Retrieve info user Error method fail" + e.getMessage(), e);
		}
		return null;
	}

	@Override
	public AccountDetail retrieveById(String id) {
		try {
			ParameterizedTypeReference<AccountDetail> ptr = new ParameterizedTypeReference<AccountDetail>(){};
			return rest.call(
					env.getProperty("config.endpoints.svc.product-svc.accs-id"), 
					HttpMethod.GET, null, ptr, id);			
			
		} catch (Exception e) {
			log.error("Retrieve info user by id Error method fail" + e.getMessage(), e);
		}
		return null;
	}

}
