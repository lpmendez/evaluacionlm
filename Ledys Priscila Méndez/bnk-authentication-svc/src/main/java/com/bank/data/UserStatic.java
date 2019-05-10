package com.bank.data;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bank.data.encrypt.IEncryptText;
import com.bank.data.text.ITextData;
import com.bank.pojo.UserDet;
import com.bank.pojo.input.UserReq;

@Service("UserStatic")
public class UserStatic implements IUserData<UserDet> {

	private ITextData text;

	private IEncryptText encrypt;
	
	public UserStatic(
			@Qualifier("BeanDataText") ITextData text,
			@Qualifier("BeanDataEncrypt") IEncryptText encrypt){
		this.text = text;
		this.encrypt = encrypt;
	}
	
	@Override
	public UserDet retrieveUser(String user, String pwd) {
		UserDet u = new UserDet();
		u.setUsername(user);
		u.setExists(true);
		if("LPMENDEZ".equals(user)){
			
			if(pwd.equals(getEncryptPwd(user, "12345"))){
				u.setActive(true);
				return u;
			}
		}
		else if("INACTIVO".equals(user)){
			if(pwd.equals(getEncryptPwd(user, "123456"))){
				u.setActive(false);
				return u;
			}
		}
		return null;
	}

	public String getEncryptPwd(String user, String pwd){
		UserReq req = new UserReq();
		req.setPassword(pwd);
		req.setUsername(user);
		String pwdEncrypt = encrypt.execute(text.getText(req));
		System.out.println(pwdEncrypt);
		return pwdEncrypt;
	}
}
