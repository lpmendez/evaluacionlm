package com.bank.process.product;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bank.data.product.IProductData;
import com.bank.pojo.product.LoanDetail;

@Service("loan")
public class LoanProcess implements IProductProcess {

	private IProductData<LoanDetail> data;
	
	public LoanProcess(@Qualifier("BeanLoanData") IProductData<LoanDetail> data) {
		this.data = data;
	}
	@Override
	public boolean existsProd(String usr, String prdId) {
		return data.retrieveByUsrAndId(usr, prdId) != null;
	}

	@Override
	public boolean existsProd(String prdId) {
		return data.retrieveById(prdId) != null;
	}
}
