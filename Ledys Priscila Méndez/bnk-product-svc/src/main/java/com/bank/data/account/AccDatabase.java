package com.bank.data.account;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bank.data.IProductData;
import com.bank.entity.BnkAxuAccountUser;
import com.bank.pojo.output.detail.AccountDetail;
import com.bank.repository.AccUsrRepository;

@Service("AccDatabase")
public class AccDatabase implements IProductData<AccountDetail> {

	private AccUsrRepository repo;
	private static SimpleDateFormat sdf;
	private Logger log;
	
	public AccDatabase(AccUsrRepository repo,
			Environment env) {
		this.repo = repo;
		sdf = new SimpleDateFormat(env.getProperty("config.format"), Locale.ENGLISH);
		this.log = LoggerFactory.getLogger(getClass());
	}
	
	@Override
	public List<AccountDetail> retrieveAllByUser(String user) {
		List<BnkAxuAccountUser> accounts = repo.findByAxuUsrcod(user);
		
		return accounts.stream().map(parse).collect(Collectors.toList());
	}
	
	static Function<BnkAxuAccountUser, AccountDetail> parse = new Function<BnkAxuAccountUser, AccountDetail>() {

		@Override
		public AccountDetail apply(BnkAxuAccountUser t) {

			AccountDetail ad = new AccountDetail();
			ad.setBalance(t.getAxuBalance().doubleValue());
			ad.setCreatedDate(sdf.format( t.getAxuCreatedDate()));
			ad.setId(t.getAxuCode());
			ad.setName(t.getBnkAccAccount().getAccName());
			ad.setStatus(t.getAxuStatus());
			ad.setUser(t.getAxuUsrcod());
			
			return ad;
		}
	};

	@Override
	public AccountDetail retrieveByUsrAndId(String user, String id) {
		BnkAxuAccountUser response =  repo.findByAxuCodeAndAxuUsrcod(id, user);
		if(response != null)
			return parse.apply(response);
		return null;
	}

	@Override
	public AccountDetail retrieveById(String id) {
		Optional<BnkAxuAccountUser> response =  repo.findById(id);
		if(response.isPresent())
			return parse.apply(response.get());
		return null;
	}

	@Override
	public boolean operation(String prdId, double amount) {
		try {
			Optional<BnkAxuAccountUser> obj = repo.findById(prdId);
			if(obj.isPresent()) {
				BnkAxuAccountUser save = obj.get();
				save.setAxuBalance(save.getAxuBalance().add(new BigDecimal(amount)));
				repo.save(save);
				return true;
			}
		}

		catch(Exception ex) {
			log.error("Error in save operation account. Error: {}.", ex.getMessage(),  ex);
		}
		return false;
	}

}
