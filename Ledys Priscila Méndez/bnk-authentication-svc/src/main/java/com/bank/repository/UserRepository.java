package com.bank.repository;

import org.springframework.data.repository.CrudRepository;

import com.bank.entity.BnkUsrUser;

public interface UserRepository extends CrudRepository<BnkUsrUser, String> {
	public BnkUsrUser findByUsrCodeAndUsrPwd(String usrCode, String usrPwd);
}
