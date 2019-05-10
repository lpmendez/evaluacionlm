package com.bank.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.bank.component.security.AuthenticationFilter;
import com.bank.component.security.AuthorizationFilter;
import com.bank.component.security.ExceptionFilter;
import com.bank.service.AuthProviderService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private Environment env;
	private AuthProviderService provider;
	
	@Autowired
	public SecurityConfiguration(
			AuthProviderService provider,
			Environment env) {
		this.provider = provider;
		this.env = env;
	}

	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider);
    }
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic().disable()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.addFilter(new AuthenticationFilter(authenticationManager(), env))
				.addFilter(new AuthorizationFilter(authenticationManager(), env))
            	.addFilter(new ExceptionFilter())
			;
	}
}
