package com.bank.data.account;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

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
	
	public AccDatabase(AccUsrRepository repo,
			Environment env) {
		this.repo = repo;
		sdf = new SimpleDateFormat(env.getProperty("config.format"), Locale.ENGLISH);
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
		return parse.apply(repo.findByAxuCodeAndAxuUsrcod(id, user));
	}

}
