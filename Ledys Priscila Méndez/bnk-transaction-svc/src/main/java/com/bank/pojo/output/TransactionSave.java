package com.bank.pojo.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionSave {
	//este nop
	private String id;
	
	private String accId;
	private String usrCode;
	private String date;
	private String description;
	private double amount;
}
