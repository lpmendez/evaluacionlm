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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bank.exceptions.ApplicationException;
import com.bank.exceptions.GlobalExceptionHandler;
import com.bank.pojo.input.Detail;
import com.bank.pojo.input.UserReq;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authManager;
	private Environment env;
	private Logger log;

	
	public AuthenticationFilter(AuthenticationManager authManager, 
			Environment env
			) {
		this.authManager = authManager;
		this.env = env;
		this.log = LoggerFactory.getLogger(getClass());
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
		try {
			UserReq user = new ObjectMapper().readValue(req.getInputStream(), UserReq.class);
			
			UsernamePasswordAuthenticationToken u = new UsernamePasswordAuthenticationToken(
					user.getUsername().toUpperCase(),
					user.getPassword(),
					new ArrayList<>());
					
			//agregando ip del requet del cliente
			Detail d = new Detail();
			d.setIp(req.getRemoteAddr());
			u.setDetails(d);
			
			return authManager.authenticate(u);
			
		}catch (ApplicationException ex) {
			log.error("User does not exists: {}", ex.getMessage(),ex);
			
	        

			GlobalExceptionHandler.createHttpResponse(res, ex);
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
		
			
		Date expDate = new Date(System.currentTimeMillis() + Integer.parseInt(env.getProperty("config.token.expire-time")));
		claims.put("username", auth.getName());
		claims.put("roles", mapper.writeValueAsString(roles));		
		claims.put("expDate", expDate);
		claims.put("ip", details.getIp()); //req.getRemoteAddr()
		
		String token = Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(new Date())
			.setExpiration(expDate)
			.signWith(SignatureAlgorithm.HS512, env.getProperty("config.token.secret-key"))
			.compact();
		
		res.addHeader("Authorization", String.format("Bearer %s", token));
	}
}
