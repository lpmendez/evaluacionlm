package com.bank.pojo.output.detail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDetail {
	private String id;
	private String name;
	private String user;
	private double balance;
	private String createdDate;
	private String status;
}
