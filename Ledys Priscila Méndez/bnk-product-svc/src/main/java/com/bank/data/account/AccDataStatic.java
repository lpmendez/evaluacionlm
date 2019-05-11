package com.bank.data.account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bank.data.IProductData;
import com.bank.pojo.output.detail.AccountDetail;

@Service("AccDataStatic")
public class AccDataStatic implements IProductData<AccountDetail> {

	@Override
	public AccountDetail retrieveByUsrAndId(String user, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AccountDetail> retrieveAllByUser(String user) {
		if("LPMENDEZ".equals(user)) {
			List<AccountDetail> list = new ArrayList<>();
			list.add(new AccountDetail());
			return list;
		}
		return null;
	}

	@Override
	public AccountDetail retrieveById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean operation(String prdId, double amount) {
		// TODO Auto-generated method stub
		return false;
	}


}
