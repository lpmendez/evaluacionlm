package com.bank.data.loan;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bank.data.IProductData;
import com.bank.pojo.output.detail.LoanDetail;

@Service("LoanDataStatic")
public class LoanDataStatic implements IProductData<LoanDetail> {

	@Override
	public List<LoanDetail> retrieveAllByUser(String user) {
		if("LPMENDEZ".equals(user)) {
			List<LoanDetail> list = new ArrayList<>();
			list.add(new LoanDetail());
			return list;
		}
		return null;
	}


}
