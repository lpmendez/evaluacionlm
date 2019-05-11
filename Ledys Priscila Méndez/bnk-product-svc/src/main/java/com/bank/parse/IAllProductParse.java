package com.bank.parse;

import java.util.List;

import com.bank.pojo.output.all.Account;

public interface IAllProductParse<T> {
	public List<Account> parse(T input);
}
