package com.bank.data.text;

import java.util.Base64;

import org.springframework.stereotype.Service;

import com.bank.pojo.input.UserReq;

@Service("B64UsrAndB64Pwd")
public class B64UsrAndB64Pwd implements ITextData {

	@Override
	public String getText(UserReq request) {
		// BASE64(usuario) + BASE64(contraseña)
		String bUser = Base64.getEncoder().encodeToString(request.getUsername().getBytes());
		String bPwd = Base64.getEncoder().encodeToString(request.getPassword().getBytes());
		return new StringBuilder().append(bUser).append(bPwd).toString();
	}

}
