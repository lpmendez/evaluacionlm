package com.bank.pojo;

import com.bank.pojo.input.Detail;

public class UserDet {
	private String username;
	private boolean exists;
	private boolean active;
	
	private Detail details;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public Detail getDetails() {
		return details;
	}
	public void setDetails(Detail details) {
		this.details = details;
	}
	public boolean isExists() {
		return exists;
	}
	public void setExists(boolean exists) {
		this.exists = exists;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	

}
