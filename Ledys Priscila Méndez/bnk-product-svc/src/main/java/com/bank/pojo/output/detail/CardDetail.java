package com.bank.pojo.output.detail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardDetail {
	private String id;
	private String name;
	private String user;
	private double limit;
	private double available;
	private double interestRate;
	private double interestAmount;
	private int monthlyCut;
	private String createdDate;
	private String status;
}
