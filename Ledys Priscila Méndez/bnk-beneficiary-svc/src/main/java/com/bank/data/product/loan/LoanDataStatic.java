package com.bank.data.product.loan;

import org.springframework.stereotype.Service;

import com.bank.data.product.IProductData;
import com.bank.pojo.product.LoanDetail;

@Service("LoanDataStatic")
public class LoanDataStatic implements IProductData<LoanDetail> {

	@Override
	public LoanDetail retrieveByUsrAndId(String usr, String id) {
		
		
		return null;
	}

	@Override
	public LoanDetail retrieveById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
