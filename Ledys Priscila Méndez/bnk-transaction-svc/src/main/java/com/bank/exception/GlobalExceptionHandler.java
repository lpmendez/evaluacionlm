package com.bank.exception;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bank.pojo.envelope.Message;

@ControllerAdvice
public class GlobalExceptionHandler 
  extends ResponseEntityExceptionHandler {

	
	@ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<Object> exception(HttpServletResponse response, ApplicationException ex) {
       return new ResponseEntity<>(new Message<>(ex.getCode() , ex.getMessage()), ex.getHttpStatus());
    }

}