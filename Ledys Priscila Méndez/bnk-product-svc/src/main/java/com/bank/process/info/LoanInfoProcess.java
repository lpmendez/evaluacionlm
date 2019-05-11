package com.bank.process.info;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bank.data.IProductData;
import com.bank.pojo.output.detail.LoanDetail;

@Service
public class LoanInfoProcess implements IInfoProcess<LoanDetail> {

	private IProductData<LoanDetail> data;
	
	public LoanInfoProcess(@Qualifier("BeanLoanData") IProductData<LoanDetail> data) {
		this.data = data;
	}
	
	@Override
	public LoanDetail retrieveBy(String usr, String id) {
		
		return data.retrieveByUsrAndId(usr, id);
	}

}
