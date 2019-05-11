package com.bank.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bank.entity.BnkLxuLoanUser;

public interface LoaUsrRepository extends CrudRepository<BnkLxuLoanUser, String> {
	public List<BnkLxuLoanUser> findByLxuUsrcod(String user);
	public BnkLxuLoanUser findByLxuCodeAndLxuUsrcod(String id, String user);
}
