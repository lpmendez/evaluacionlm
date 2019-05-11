package com.bank.pojo.output.all;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
	private List<Account> loan;
	private List<Account> creditCard;
	private List<Account> personal;
}
