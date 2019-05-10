package com.bank.data;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bank.entity.BnkUsrUser;
import com.bank.pojo.UserDet;
import com.bank.repository.UserRepository;

@Service("UserDatabase")
public class UserDatabase implements IUserData<UserDet> {

	private UserRepository repo;
	private Environment env;
	private Logger log;
	
	@Value("${config.tries}")
	private int tries;
	
	public UserDatabase(UserRepository repo,
			Environment env){
		this.repo = repo;
		this.env = env;
		this.log = LoggerFactory.getLogger(getClass());
	}
	@Override
	public UserDet retrieveUser(String user, String pwd) {
		UserDet det = new UserDet();
		try {
		
			if(repo.exists(user)){
				BnkUsrUser usr = repo.findByUsrCodeAndUsrPwd(user, pwd);
				if(usr != null) {
					//si la contra es correcta
					det.setExists(true);
					det.setUsername(user);
					
					
					if("A".equals(usr.getUsrStatus()) && usr.getUsrTries().intValue() < tries ){
						//si esta activo devolverlo normal
						usr.setUsrTries(new BigDecimal(0));
						repo.save(usr);
						
						det.setActive(true);
						return det;
					}
					det.setActive(false);
					return det;
				}
				//actualizar tries
				usr = repo.findOne(user);
				usr.setUsrTries(usr.getUsrTries().add(new BigDecimal(1)));
				repo.save(usr);
			}
		}
		catch(Exception ex) {
			log.error("Error in database. Error: {}. User: {}", ex.getMessage(), user, ex);
		}
		
		return null;
	}

}
