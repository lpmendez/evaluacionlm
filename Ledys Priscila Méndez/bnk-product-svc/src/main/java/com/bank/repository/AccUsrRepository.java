package com.bank.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bank.entity.BnkAxuAccountUser;

public interface AccUsrRepository extends CrudRepository<BnkAxuAccountUser, String>{
	public List<BnkAxuAccountUser> findByAxuUsrcod(String user);
	public BnkAxuAccountUser findByAxuCodeAndAxuUsrcod(String id, String user);
}
