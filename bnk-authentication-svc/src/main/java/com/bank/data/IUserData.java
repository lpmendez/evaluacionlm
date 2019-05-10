package com.bank.data;

public interface IUserData<T> {
	public T retrieveUser(String user, String pwd);
}
