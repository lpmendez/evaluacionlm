package com.bank.data.text;

import org.springframework.stereotype.Service;

import com.bank.pojo.input.UserReq;

@Service("SameText")
public class SameText implements ITextData {

	@Override
	public String getText(UserReq request) {
		return request.getPassword();
	}

}
