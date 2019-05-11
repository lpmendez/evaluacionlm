package com.bank.pojo.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessReq {
	private String user;
	private String type; //tipo de cuenta o prod
	private String account;
	private String startDate;
	private String endDate;
}
