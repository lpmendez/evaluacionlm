package com.bank.pojo.output.card;

import com.bank.pojo.output.Transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Card extends Transaction {
	private double limit;
	private double available;
	private double interestAmount;
	private double interestRate;
	private double monthlyCut;
}
