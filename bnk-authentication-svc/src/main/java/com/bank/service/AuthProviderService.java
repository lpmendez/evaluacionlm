package com.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bank.data.IUserData;
import com.bank.pojo.UserPojo;


@Service
public class AuthProviderService  implements AuthenticationProvider{
	private IUserData<UserPojo> data;
	
	@Autowired
	public AuthProviderService(@Qualifier("BeanDataUser") IUserData<UserPojo> data) {
		this.data = data;
	}
	@Override
	public Authentication authenticate(Authentication authentication) {

		// Determine username
		String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED"
				: authentication.getName();
		
		String pwd = authentication.getCredentials().toString();
		
//		List<GrantedAuthority> authorities;
		UserPojo userPojo = data.retrieveUser(username, pwd);

		//logica de intentos.. ok
		if(userPojo == null)
			throw new UsernameNotFoundException(username);
		else if(userPojo.getLoginTries() >= 5) {
			//un throw
		}
		
		UserDetails user = new	User(userPojo.getUsername(), 
				userPojo.getPassword() == null ? "" : userPojo.getPassword(), 
				true, true, true,true,
				null);
		
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
