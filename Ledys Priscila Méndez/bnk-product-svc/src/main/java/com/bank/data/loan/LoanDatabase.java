package com.bank.data.loan;

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
import com.bank.entity.BnkLxuLoanUser;
import com.bank.pojo.output.detail.LoanDetail;
import com.bank.repository.LoaUsrRepository;

@Service("LoanDatabase")
public class LoanDatabase implements IProductData<LoanDetail> {

	private LoaUsrRepository repo;
	private static SimpleDateFormat sdf;
	private Logger log;
	
	public LoanDatabase(LoaUsrRepository repo,
			Environment env) {
		this.repo = repo;
		sdf = new SimpleDateFormat(env.getProperty("config.format"), Locale.ENGLISH);
		this.log = LoggerFactory.getLogger(getClass());
	}
	
	@Override
	public List<LoanDetail> retrieveAllByUser(String user) {
		List<BnkLxuLoanUser> accounts = repo.findByLxuUsrcod(user);
		
		return accounts.stream().map(parse).collect(Collectors.toList());
	}
	
	static Function<BnkLxuLoanUser, LoanDetail> parse = new Function<BnkLxuLoanUser, LoanDetail>() {

		@Override
		public LoanDetail apply(BnkLxuLoanUser t) {

			LoanDetail obj = new LoanDetail();
			obj.setDebt(t.getLxuDebt().doubleValue());
			obj.setInterestAmount(t.getLxuInterestAmount().doubleValue());
			obj.setInterestRate(t.getLxuInterestRate().doubleValue());
			obj.setTotal(t.getLxuTotal().doubleValue());
			obj.setCreatedDate(sdf.format( t.getLxuCreatedDate()));
			obj.setId(t.getLxuCode());
			obj.setName(t.getBnkLoaLoan().getLoaName());
			obj.setStatus(t.getLxuStatus());
			obj.setUser(t.getLxuUsrcod());
			
			return obj;
		}
	};

	@Override
	public LoanDetail retrieveByUsrAndId(String user, String id) {
		BnkLxuLoanUser response =  repo.findByLxuCodeAndLxuUsrcod(id, user);
		if(response != null)
			return parse.apply(response);
		return null;
	}

	@Override
	public LoanDetail retrieveById(String id) {
		Optional<BnkLxuLoanUser> response =  repo.findById(id);
		if(response.isPresent())
			return parse.apply(response.get());
		return null;
	}

	@Override
	public boolean operation(String prdId, double amount) {
		try {
			Optional<BnkLxuLoanUser> obj = repo.findById(prdId);
			if(obj.isPresent()) {
				BnkLxuLoanUser save = obj.get();
				save.setLxuDebt(save.getLxuDebt().add(new BigDecimal(amount)));
				
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
