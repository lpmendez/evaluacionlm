package com.bank.pojo.envelope;

public class Status {
	private String code;
	private String result;

	public Status() {}
	
	public Status(String code, String result) {
		this.code = code;
		this.result = result;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
