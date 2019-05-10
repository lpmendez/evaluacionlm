package com.bank.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AppplicationConfiguration {
	Environment env;
	
	@Autowired
	public AppplicationConfiguration(Environment env){
		this.env = env;
	}
	
}
