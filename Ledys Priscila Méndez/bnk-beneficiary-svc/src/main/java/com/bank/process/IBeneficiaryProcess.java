package com.bank.process;

import com.bank.pojo.input.Beneficiary;

public interface IBeneficiaryProcess {
	public void save(Beneficiary request);
	public void update(Beneficiary request);

	public void delete(String id, String usr);
}
