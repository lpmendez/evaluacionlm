package com.bank.data;

import org.springframework.stereotype.Service;

import com.bank.pojo.UserPojo;

@Service("UserStatic")
public class UserStatic implements IUserData<UserPojo> {

	@Override
	public UserPojo retrieveUser(String user, String pwd) {
		if("LPMENDEZ".equals(user)){
			UserPojo u = new UserPojo();
			u.setLoginTries(0);
			u.setPassword("");
			u.setUsername("LPMENDEZ");
			return u;
		}
		return null;
	}

}
