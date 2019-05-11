package com.bank.component.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import com.bank.exceptions.ApplicationException;
import com.bank.exceptions.GlobalExceptionHandler;
import com.bank.utils.ResponseCode;
import com.bank.utils.ResponseMsg;

public class ExceptionFilter extends ExceptionTranslationFilter {
	
	public ExceptionFilter() {
		super(new BasicAuthenticationEntryPoint());
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		if(((HttpServletRequest) req).getUserPrincipal() == null) {
			GlobalExceptionHandler.createHttpResponse(
				(HttpServletResponse) res,
				new ApplicationException(
					HttpStatus.FORBIDDEN,
					ResponseCode.INVALID,
					ResponseMsg.INVALID
				)
			);
			
			return;
		}
		
		chain.doFilter(req, res);
	}
}
