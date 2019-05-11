package com.bank.pojo.input.operations;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperationReq {
	private String from;
	private String to;
	private double amount;
	private String user;
	private String description;
}
