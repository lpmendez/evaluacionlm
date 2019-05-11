package com.bank.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bank.entity.BnkCxuCardUser;

public interface CrdUsrRepository extends CrudRepository<BnkCxuCardUser, String> {
	public List<BnkCxuCardUser> findByCxuUsrcod(String user);
}
