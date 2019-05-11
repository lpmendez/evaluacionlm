package com.bank.pojo.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanDetail extends ProdDetail{
	
	private double total;
	private double debt;
	private double interestRate;
	private double interestAmount;
	
}
