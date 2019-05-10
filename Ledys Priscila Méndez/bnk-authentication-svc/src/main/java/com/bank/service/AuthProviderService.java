package com.bank.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.bank.data.IUserData;
import com.bank.data.encrypt.IEncryptText;
import com.bank.data.text.ITextData;
import com.bank.exceptions.ApplicationException;
import com.bank.pojo.UserDet;
import com.bank.pojo.input.Detail;
import com.bank.pojo.input.UserReq;
import com.bank.utils.ResponseCode;
import com.bank.utils.ResponseMsg;


@Service
public class AuthProviderService  implements AuthenticationProvider{
	private IUserData<UserDet> data;
	private Environment env;
	private ITextData text;
	private IEncryptText encrypt;
	
	@Autowired
	public AuthProviderService(@Qualifier("BeanDataUser") IUserData<UserDet> data,
			@Qualifier("BeanDataText") ITextData text,
			@Qualifier("BeanDataEncrypt") IEncryptText encrypt,
			Environment env) {
		this.data = data;
		this.env = env;
		this.text = text;
		this.encrypt = encrypt;
	}
	@Override
	public Authentication authenticate(Authentication authentication) {

		// Determine username
		String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED"
				: authentication.getName();
		String pwd = authentication.getCredentials().toString();

		UserReq input = new UserReq();
		input.setPassword(pwd);
		input.setUsername(username);
		
		//transformar pwd a formato especificado
		pwd = encrypt.execute(text.getText(input));
		
		
//		a el no le importa que hizo dentro.. 
		// como que si existe el usuario pero la contra es la mala,, no,  solo quiere saber si está correcto
		UserDet userPojo = data.retrieveUser(username, pwd);

		//logica de intentos.. ok
		if(userPojo == null || !userPojo.isExists()) {
			throw new ApplicationException(HttpStatus.UNAUTHORIZED,
	        		ResponseCode.ERROR, ResponseMsg.ERROR);
		}
		if(!userPojo.isActive()) {
			throw new ApplicationException(HttpStatus.BAD_REQUEST,
	        		ResponseCode.INACTIVE, ResponseMsg.INACTIVE);
		}
		userPojo.setDetails((Detail) authentication.getDetails());
		
		UserDetails user = new	User(userPojo.getUsername(), 
				"", true, true, true,true,
				new ArrayList<>());
		
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
								user, authentication.getCredentials(),
								user.getAuthorities());
		
		result.setDetails(userPojo.getDetails());
		return result;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class
				.isAssignableFrom(authentication);
	}

}
