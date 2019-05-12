package com.bank.data.beneficiary;

import com.bank.pojo.output.beneficiary.Beneficiary;

public interface IBeneficiaryData {

	public Beneficiary findByIdAndUsr(String benid, String usr);
}
