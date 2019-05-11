package com.bank.pojo.input.operation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperationReq {
	private String from;
	private String to;
	private double amount;
	private String user;
}
