package com.bank.parse;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.bank.pojo.output.all.Account;
import com.bank.pojo.output.detail.LoanDetail;

public class LoanParse implements IAllProductParse<List<LoanDetail>>{
	
	
	static Function<LoanDetail, Account> parseLoans = new Function<LoanDetail, Account>() {

		@Override
		public Account apply(LoanDetail t) {
			Account l = new Account();
			l.setId(t.getId());
			l.setName(t.getName());
			return l;
		}
	};
	@Override
	public List<Account> parse(List<LoanDetail> input) {
		return input.stream().map(parseLoans).collect(Collectors.toList());
	}
}
