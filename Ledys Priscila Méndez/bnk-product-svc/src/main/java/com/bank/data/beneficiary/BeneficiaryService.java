package com.bank.data.beneficiary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.bank.pojo.output.beneficiary.Beneficiary;
import com.bank.utils.RestAPI;

@Service("BeneficiaryService")
public class BeneficiaryService implements IBeneficiaryData {

	private RestAPI rest;
	private Environment env;
	private Logger log;

	
	public BeneficiaryService(RestAPI rest,Environment env){
		this.rest = rest;
		this.env = env;
		this.log = LoggerFactory.getLogger(getClass());		
	}	
	@Override
	public Beneficiary findByIdAndUsr(String id, String usr) {
		try {
			ParameterizedTypeReference<Beneficiary> ptr = new ParameterizedTypeReference<Beneficiary>(){};
			return rest.call(
					env.getProperty("config.endpoints.svc.beneficiary-svc.exists"), 
					HttpMethod.GET, null, ptr, id, usr);			
			
		} catch (Exception e) {
			log.error("Save transaction Error method fail" + e.getMessage(), e);
		}
		return null;
	}

}
