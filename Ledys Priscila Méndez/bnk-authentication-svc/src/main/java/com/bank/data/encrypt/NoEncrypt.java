package com.bank.data.encrypt;

import org.springframework.stereotype.Service;

@Service("NoEncrypt")
public class NoEncrypt implements IEncryptText {

	@Override
	public String execute(String texto) {
		return texto;
	}

}
