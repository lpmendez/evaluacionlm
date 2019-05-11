package com.bank.parse;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.bank.pojo.output.all.Account;
import com.bank.pojo.output.detail.AccountDetail;

public class AccountParse implements IAllProductParse<List<AccountDetail>>{

	@Override
	public List<Account> parse(List<AccountDetail> input) {
		return input.stream().map(parseCards).collect(Collectors.toList());
	}

	static Function<AccountDetail, Account> parseCards = new Function<AccountDetail, Account>() {

		@Override
		public Account apply(AccountDetail t) {
			Account l = new Account();
			l.setId(t.getId());
			l.setName(t.getName());
			return l;
		}
	};

}
