package com.bank.controller;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.bank.exception.ApplicationException;
import com.bank.utils.ResponseMsg;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles("testing")
public class TransTest {
	@Autowired
	private TransactionController controller;

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	private String user;
	
	@Before
	public void setUp() {
		user = "LPMENDEZ";
	}
	
	@Test
	public void accountNotExist() {
		exceptionRule.expect(ApplicationException.class);
		exceptionRule.expectMessage(ResponseMsg.INVALID);
		controller.getTransByAccAndProd(user, "0003", "01-Jan-2019", "28-Feb-2019", "loan");
	}


	@Test
	public void accountDateRangeExceed() {

		//para el caso tomaré 3 meses como 90 dias ok
		exceptionRule.expect(ApplicationException.class);
		exceptionRule.expectMessage(ResponseMsg.ERROR);
		controller.getTransByAccAndProd(user, "000001", "03-Dec-2018", "10-Mar-2019", "loan");
	}

	@Test
	public void accountDateRangeFail() {

		//para el caso tomaré 3 meses como 90 dias ok
		exceptionRule.expect(ApplicationException.class);
		exceptionRule.expectMessage(ResponseMsg.ERROR);
		controller.getTransByAccAndProd(user, "000001", "10-Mar-2019", "03-Dec-2018", "loan");
	}
}
