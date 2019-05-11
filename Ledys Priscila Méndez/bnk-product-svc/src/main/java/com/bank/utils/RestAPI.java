package com.bank.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestAPI {
		
	private RestTemplate restTemplate;
	
	@Autowired
	public RestAPI(RestTemplate restTemplate){
		this.restTemplate = restTemplate;
	} 
	
	public <T, U> U call(String url, HttpMethod method, T requestObject, ParameterizedTypeReference<U> responseType,
		Object... uriParams) throws RestClientException{
		HttpEntity<T> request; 
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		ResponseEntity<U> response;

		headers.add("Authorization", "Basic");
		headers.add("Content-Type", "application/json");

		request = new HttpEntity<>(requestObject, headers);
		
		response = restTemplate.exchange(url, method, request, responseType, uriParams);

		return response.getBody();
	}
}
