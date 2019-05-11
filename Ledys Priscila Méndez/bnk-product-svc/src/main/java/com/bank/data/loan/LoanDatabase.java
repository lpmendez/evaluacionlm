package com.bank.data.loan;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
	
	public LoanDatabase(LoaUsrRepository repo,
			Environment env) {
		this.repo = repo;
		sdf = new SimpleDateFormat(env.getProperty("config.format"));
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

}
