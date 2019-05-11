package com.bank.data.product.card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.bank.data.product.IProductData;
import com.bank.pojo.product.CardDetail;
import com.bank.utils.RestAPI;

@Service("CardDataService")
public class CardDataService implements IProductData<CardDetail> {

	private RestAPI rest;
	private Environment env;
	private Logger log;

	
	public CardDataService(RestAPI rest,Environment env){
		this.rest = rest;
		this.env = env;
		this.log = LoggerFactory.getLogger(getClass());		
	}	
	
	@Override
	public CardDetail retrieveByUsrAndId(String usr, String id) {
		
		try {
			ParameterizedTypeReference<CardDetail> ptr = new ParameterizedTypeReference<CardDetail>(){};
			return rest.call(
					env.getProperty("config.endpoints.svc.product-svc.cards"), 
					HttpMethod.GET, null, ptr, usr, id);			
			
		} catch (Exception e) {
			log.error("Retrieve info cards Error method fail" + e.getMessage(), e);
		}
		return null;
	}

	@Override
	public CardDetail retrieveById(String id) {

		try {
			ParameterizedTypeReference<CardDetail> ptr = new ParameterizedTypeReference<CardDetail>(){};
			return rest.call(
					env.getProperty("config.endpoints.svc.product-svc.cards-id"), 
					HttpMethod.GET, null, ptr, id);			
			
		} catch (Exception e) {
			log.error("Retrieve info cards by id Error method fail" + e.getMessage(), e);
		}
		return null;
	}

}
