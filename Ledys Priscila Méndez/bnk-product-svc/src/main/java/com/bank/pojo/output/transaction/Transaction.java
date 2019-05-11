package com.bank.pojo.output.transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {
	//este nop
	private String id;
	
	private String accId;
	private String usrCode;
	private String date;
	private String description;
	private double amount;
}
