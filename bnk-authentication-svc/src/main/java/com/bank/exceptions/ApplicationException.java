package com.bank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;

public class ApplicationException extends AccessDeniedException{
	private static final long serialVersionUID = 1L;
	private HttpStatus httpStatus;
	private String code;
	private String message;
	
	public ApplicationException(HttpStatus httpStatus, String code, String message) {
		super(message);
		
		this.httpStatus = httpStatus;
		this.code = code;
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
