package com.bank.exceptions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bank.pojo.envelope.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GlobalExceptionHandler {
	private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	public static void createHttpResponse(HttpServletResponse response,  ApplicationException exception) {
		try {
			PrintWriter writer = response.getWriter();
			
			response.setStatus(Integer.parseInt(exception.getCode()));
			response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
			
			writer.write(createBody(exception));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private static String createBody(ApplicationException exception){
		ObjectMapper mapper = new ObjectMapper();
		String response = null;
	        
        try {
        	
        	response = mapper.writeValueAsString(new Message<>(exception.getCode() , exception.getMessage()));
        } catch(Exception e) {
        	log.error(e.getMessage(),e);
        }
        
		return response;
	}
}