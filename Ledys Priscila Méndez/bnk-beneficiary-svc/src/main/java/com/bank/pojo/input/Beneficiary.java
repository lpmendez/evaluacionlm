	package com.bank.pojo.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Beneficiary {
	private String name;
	private String account;
	private String type;
	private String email;
	//esto no me lo mandan
	private String usr;
	//para actualizar
	private String id;
}
