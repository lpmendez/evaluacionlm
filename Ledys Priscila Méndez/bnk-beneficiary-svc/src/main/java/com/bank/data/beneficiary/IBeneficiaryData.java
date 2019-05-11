package com.bank.data.beneficiary;

import com.bank.pojo.input.Beneficiary;

public interface IBeneficiaryData {
	public boolean save(Beneficiary b);
	public boolean update(Beneficiary b);
	public boolean delete(String id);
	public boolean exists(String id);
	public boolean existsBy(String usr, String account);
	public boolean existsByUsrAndId(String usr, String benid);
}
