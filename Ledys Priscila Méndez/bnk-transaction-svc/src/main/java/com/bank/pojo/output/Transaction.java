package com.bank.pojo.output;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {
	private String id;
	private String startDate;
	private String endDate;
	private List<TransactionDetail> transactions;
}
