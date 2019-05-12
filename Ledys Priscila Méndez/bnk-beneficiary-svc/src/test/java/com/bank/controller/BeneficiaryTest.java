package com.bank.controller;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.bank.exception.ApplicationException;
import com.bank.pojo.input.Beneficiary;
import com.bank.utils.ResponseMsg;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles("testing")
public class BeneficiaryTest extends Mockito{
	@Autowired
	private BeneficiaryController controller;

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	private String user;
	private HttpServletResponse mockResponse;
	
	@Before
	public void setUp() {
		user = "LPMENDEZ";
		mockResponse = mock(HttpServletResponse.class);
	}

	@Test
	public void success() {
		Beneficiary info = new Beneficiary();
		info.setAccount("1");
		info.setEmail("prueba@gmail.com");
		info.setName("Ledys");
		info.setType("ACC");
		controller.save(mockResponse, user, info);
	}
	
	@Test
	public void accountNotExist() {

		exceptionRule.expect(ApplicationException.class);
		exceptionRule.expectMessage(ResponseMsg.INVALID);
		
		Beneficiary info = new Beneficiary();
		info.setAccount("2");
		info.setEmail("prueba@gmail.com");
		info.setName("Ledys");
		info.setType("ACC");
		controller.save(mockResponse, user, info);
	}


	@Test
	public void noEmail() {

		exceptionRule.expect(ApplicationException.class);
		exceptionRule.expectMessage(ResponseMsg.NO_DATA);
		
		Beneficiary info = new Beneficiary();
		info.setAccount("1");
		info.setName("Ledys");
		info.setType("ACC");
		controller.save(mockResponse, user, info);
	}

	@Test
	public void invalidEmail() {

		exceptionRule.expect(ApplicationException.class);
		exceptionRule.expectMessage("Invalid email");
		
		Beneficiary info = new Beneficiary();
		info.setAccount("1");
		info.setEmail("prueba");
		info.setName("Ledys");
		info.setType("ACC");
		controller.save(mockResponse, user, info);
	}

	@Test
	public void duplicatedBeneficiary() {

		exceptionRule.expect(ApplicationException.class);
		exceptionRule.expectMessage(ResponseMsg.DUPLICATED);
		
		Beneficiary info = new Beneficiary();
		info.setAccount("4");
		info.setEmail("prueba@gmail.com");
		info.setName("Ledys");
		info.setType("ACC");
		controller.save(mockResponse,user, info);
	}
}
