package com.bank.pojo;

public class UserPojo {
	private String username;
	private String password;
	private int loginTries;
	private String status;
	private Detail details;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getLoginTries() {
		return loginTries;
	}
	public void setLoginTries(int loginTries) {
		this.loginTries = loginTries;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Detail getDetails() {
		return details;
	}
	public void setDetails(Detail details) {
		this.details = details;
	}
	
	
	

}
