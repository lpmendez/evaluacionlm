package com.bank.pojo.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardDetail extends ProdDetail {
	
	private double limit;
	private double available;
	private double interestRate;
	private double interestAmount;
	private int monthlyCut;
	
}
