package com.bank.component.security;

import java.io.IOException;
import java.util.Date;

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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.bank.exceptions.ApplicationException;
import com.bank.exceptions.GlobalExceptionHandler;
import com.bank.pojo.input.Detail;
import com.bank.utils.ResponseCode;
import com.bank.utils.ResponseMsg;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class AuthorizationFilter extends BasicAuthenticationFilter {
	private Environment env;
	private Logger log;

	public AuthorizationFilter(AuthenticationManager authenticationManager, Environment env) {
		super(authenticationManager);
		
		this.env = env;
		this.log = LoggerFactory.getLogger(getClass());
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader("Authorization");

		if (header != null && header.startsWith("Bearer")) {
			try {
				Claims claims = getAuthentication(header);
				//creando nuevo token
				
				String newToken = Jwts.builder()
						.setClaims(claims)
						.setIssuedAt(new Date())
						.setExpiration(new Date(System.currentTimeMillis() + Integer.parseInt(env.getProperty("config.token-expire-time"))))
						.signWith(SignatureAlgorithm.HS512, env.getProperty("config.token-secret-key"))
						.compact();
					
				res.addHeader("Authorization", newToken);
						
				SecurityContextHolder.getContext().setAuthentication(loadAuthUser(claims));
				
			} catch(ApplicationException e) {
				GlobalExceptionHandler.createHttpResponse(res, e);
				
				return;
			}
			
		}
		chain.doFilter(req, res);
	}
	
	private Claims getAuthentication(String token) throws IOException {
		try {
			return Jwts.parser()
					.setSigningKey(env.getProperty("config.token-secret-key"))
					.parseClaimsJws(token.replace("Bearer ", ""))
					.getBody();
			
		}
        catch(ExpiredJwtException e){
	        log.error("Token Expired: {}", e.getMessage(), e);
	        throw new ApplicationException(HttpStatus.UNAUTHORIZED, ResponseCode.ERROR, ResponseMsg.ERROR);
        }
        catch (MalformedJwtException | SignatureException | UnsupportedJwtException e){
            log.error("UnsupportedToken: {}", e.getMessage(), e);
	        throw new ApplicationException(HttpStatus.UNAUTHORIZED, ResponseCode.ERROR, ResponseMsg.ERROR);
        } 
	}
	
	private Authentication loadAuthUser(Claims claims) {
		String username;
		Detail details = new Detail();
		
		username = claims.get("username", String.class);
		
		
		details.setIp(claims.get("ip", String.class));
		
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
				username, null,null);

		result.setDetails(details);
		
		return result;
	}
}
