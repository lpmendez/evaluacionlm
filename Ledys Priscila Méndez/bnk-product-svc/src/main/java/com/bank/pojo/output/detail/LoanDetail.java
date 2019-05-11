package com.bank.pojo.output.detail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanDetail {
	private String id;
	private String name;
	private String user;
	private double total;
	private double debt;
	private double interestRate;
	private double interestAmount;
	private String createdDate;
	private String status;
}
