package com.bank.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.bank.exceptions.ApplicationException;
import com.bank.utils.ResponseMsg;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles("testing")
public class AuthTest {

	@Autowired
	private AuthProviderService service;
	
	@Before
	public void setupTest() {
	}

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	
	@Test
	public void validateSuccess(){
		Authentication a = new UsernamePasswordAuthenticationToken("LPMENDEZ", "12345");
		Authentication response = service.authenticate(a);
		Assert.assertEquals(a.getPrincipal(), response.getName());
	}

	@Test
	public void invalidUser(){
		exceptionRule.expect(ApplicationException.class);
		exceptionRule.expectMessage(ResponseMsg.ERROR);
		Authentication a = new UsernamePasswordAuthenticationToken("LPMENDEZs", "123456");
		service.authenticate(a);
	}
	
	@Test
	public void inactiveUser(){
		exceptionRule.expect(ApplicationException.class);
		exceptionRule.expectMessage(ResponseMsg.INACTIVE);
		Authentication a = new UsernamePasswordAuthenticationToken("INACTIVO", "123456");
		service.authenticate(a);
	}

	@Test
	public void inactiveUserWithWrongPwd(){
		exceptionRule.expect(ApplicationException.class);
		exceptionRule.expectMessage(ResponseMsg.ERROR);
		Authentication a = new UsernamePasswordAuthenticationToken("INACTIVO", "12345");
		service.authenticate(a);
	}
}
