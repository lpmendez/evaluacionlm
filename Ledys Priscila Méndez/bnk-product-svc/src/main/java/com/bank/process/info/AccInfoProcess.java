package com.bank.process.info;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bank.data.IProductData;
import com.bank.pojo.output.detail.AccountDetail;

@Service
public class AccInfoProcess implements IInfoProcess<AccountDetail> {

	private IProductData<AccountDetail> data;
	
	public AccInfoProcess(@Qualifier("BeanAccData") IProductData<AccountDetail> data) {
		this.data = data;
	}
	
	@Override
	public AccountDetail retrieveBy(String usr, String id) {
		
		return data.retrieveByUsrAndId(usr, id);
	}

}
