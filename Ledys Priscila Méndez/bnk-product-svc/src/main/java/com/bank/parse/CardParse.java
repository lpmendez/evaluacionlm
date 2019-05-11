package com.bank.parse;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.bank.pojo.output.all.Account;
import com.bank.pojo.output.detail.CardDetail;

public class CardParse implements IAllProductParse<List<CardDetail>>{

	@Override
	public List<Account> parse(List<CardDetail> input) {
		return input.stream().map(parseCards).collect(Collectors.toList());
	}

	static Function<CardDetail, Account> parseCards = new Function<CardDetail, Account>() {

		@Override
		public Account apply(CardDetail t) {
			Account l = new Account();
			l.setId(t.getId());
			l.setName(t.getName());
			return l;
		}
	};

}
