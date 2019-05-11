package com.bank.pojo.output.loan;

import com.bank.pojo.output.Transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Loan extends Transaction {
	private double total;
	private double debt;
	private double interestRate;
	private double interestAmount;
}
