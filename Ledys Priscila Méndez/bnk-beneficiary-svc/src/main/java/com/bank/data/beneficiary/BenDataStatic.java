package com.bank.data.beneficiary;

import org.springframework.stereotype.Service;

import com.bank.pojo.input.Beneficiary;

@Service("BenDataStatic")
public class BenDataStatic implements IBeneficiaryData {

	@Override
	public boolean save(Beneficiary b) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Beneficiary b) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existsBy(String usr, String account) {
		if("LPMENDEZ".equals(usr) && "4".equals(account)) {
			return true;
		}
		return false;
	}

}
