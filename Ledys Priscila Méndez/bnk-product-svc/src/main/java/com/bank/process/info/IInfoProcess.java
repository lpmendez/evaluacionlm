package com.bank.process.info;

public interface IInfoProcess<T> {
	public T retrieveBy(String usr, String id);
	public T retrieveById(String id);
}
