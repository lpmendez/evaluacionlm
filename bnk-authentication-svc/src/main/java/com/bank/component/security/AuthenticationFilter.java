package com.bank.component.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bank.exceptions.ApplicationException;
import com.bank.pojo.Detail;
import com.bank.pojo.UserPojo;
import com.bank.utils.ResponseCode;
import com.bank.utils.ResponseMsg;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authManager;
	private Environment env;
	private Logger log;

	String response;

	String responseMsg;

	String expiresDay;

	String name;
	
	public AuthenticationFilter(AuthenticationManager authManager, Environment env) {
		this.authManager = authManager;
		this.env = env;
		this.log = LoggerFactory.getLogger(getClass());
		response = env.getProperty("config.variables.response-code");
		responseMsg = env.getProperty("config.variables.response-msg");
		expiresDay = env.getProperty("config.variables.expires-days");
		name = env.getProperty("config.variables.name");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
		try {
			UserPojo user = new ObjectMapper().readValue(req.getInputStream(), UserPojo.class);

			return authManager.authenticate(
				new UsernamePasswordAuthenticationToken(
					user.getUsername(),
					user.getPassword(),
					new ArrayList<>()
				)
			);
		}catch (UsernameNotFoundException ex) {
			log.error("User does not exists: {}", ex.getMessage(),ex);
			
	        throw new ApplicationException(HttpStatus.BAD_REQUEST,
	        		ResponseCode.INACTIVE, ResponseMsg.INACTIVE);
	        
//			res.setStatus(Integer.parseInt(ResponseCode.INACTIVE));
//			
//			res.addHeader(response, ResponseCode.INACTIVE);
//			res.addHeader(responseMsg, ResponseMsg.INACTIVE);
		} //catch otro exception
		catch (Exception e) {
			log.error("Attemp Authentication error: {}", e.getMessage(),e);
			
		}
		return null;
	}

	@Override
	protected void successfulAuthentication(
			HttpServletRequest req,
			HttpServletResponse res,
			FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> claims = new HashMap<>();
		List<String> roles = new ArrayList<>();
		
		auth.getAuthorities().forEach(authority -> roles.add(authority.getAuthority()));
		
		Detail details = (Detail) auth.getDetails();
		
		//validaciones....

		/* ESTO ES PARA DEVOLVER UN BODY--
		 * 
		 * Status s = new Status();
		 * res.setContentType("application/json");
		 * s.setCode(ResponseCode.PWD_WRONG);
		 * s.setResult(ResponseMsg.PWD_WRONG);
		 * res.getWriter().print(mapper.writeValueAsString(new Message<>(s, false)));
		 * 
		 * */
//		if(details.isWrongPwd()){
//			res.addHeader(response, ResponseCode.CON_WRONG);
//			res.addHeader(responseMsg, ResponseMsg.CON_WRONG);
//		}
//		else if(details.isDisabled()){
//			res.addHeader(response, ResponseCode.USR_DISABLED);
//			res.addHeader(responseMsg, ResponseMsg.USR_DISABLED);
//		}
//		else if(details.isLocked()){
//			res.addHeader(response, ResponseCode.USR_LOCKED);
//			res.addHeader(responseMsg, ResponseMsg.USR_LOCKED);
//		}
//		else{
//			
//			if(details.isResetPwd()){
//				res.addHeader(response, ResponseCode.CON_RESET);
//				res.addHeader(responseMsg, ResponseMsg.CON_RESET);
//			}
//			else if(details.isReminderPwd()){
//				res.addHeader(response, ResponseCode.CON_REMIND);
//				res.addHeader(responseMsg, ResponseMsg.CON_REMIND);
//			}
//			res.addHeader(name, details.getName());
//			res.addIntHeader(expiresDay, details.getExpires());
			
			Date expDate = new Date(System.currentTimeMillis() + Integer.parseInt(env.getProperty("config.token-expire-time")));
			claims.put("username", auth.getName());
			claims.put("roles", mapper.writeValueAsString(roles));		
			claims.put("expDate", expDate);
			claims.put("ip", details.getIp());
			
			String token = Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date())
				.setExpiration(expDate)
				.signWith(SignatureAlgorithm.HS512, env.getProperty("config.token-secret-key"))
				.compact();
			
			res.addHeader("Authorization", token);
//		}
	}
}
