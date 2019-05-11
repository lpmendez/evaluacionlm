package com.bank.data.card;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bank.data.IProductData;
import com.bank.entity.BnkCxuCardUser;
import com.bank.pojo.output.detail.CardDetail;
import com.bank.repository.CrdUsrRepository;

@Service("CardDatabase")
public class CardDatabase implements IProductData<CardDetail> {

	private CrdUsrRepository repo;
	private static SimpleDateFormat sdf;
	
	public CardDatabase(CrdUsrRepository repo,
			Environment env) {
		this.repo = repo;
		sdf = new SimpleDateFormat(env.getProperty("config.format"));
	}
	
	@Override
	public List<CardDetail> retrieveAllByUser(String user) {
		List<BnkCxuCardUser> accounts = repo.findByCxuUsrcod(user);
		
		return accounts.stream().map(parse).collect(Collectors.toList());
	}
	
	static Function<BnkCxuCardUser, CardDetail> parse = new Function<BnkCxuCardUser, CardDetail>() {

		@Override
		public CardDetail apply(BnkCxuCardUser t) {

			CardDetail obj = new CardDetail();
			obj.setAvailable(t.getCxuAvailable().doubleValue());
			obj.setInterestAmount(t.getCxuInterestAmount().doubleValue());
			obj.setInterestRate(t.getCxuInterestRate().doubleValue());
			obj.setLimit(t.getCxuLimit().doubleValue());
			obj.setMonthlyCut(t.getCxuMonthlyCut());
			obj.setCreatedDate(sdf.format( t.getCxuCreatedDate()));
			obj.setId(t.getCxuCode());
			obj.setName(t.getBnkCrdCard().getCrdName());
			obj.setStatus(t.getCxuStatus());
			obj.setUser(t.getCxuUsrcod());
			
			return obj;
		}
	};

}
