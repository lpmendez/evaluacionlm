package com.bank.data.card;

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
import com.bank.entity.BnkCxuCardUser;
import com.bank.pojo.output.detail.CardDetail;
import com.bank.repository.CrdUsrRepository;

@Service("CardDatabase")
public class CardDatabase implements IProductData<CardDetail> {

	private CrdUsrRepository repo;
	private static SimpleDateFormat sdf;
	private Logger log;
	
	public CardDatabase(CrdUsrRepository repo,
			Environment env) {
		this.repo = repo;
		sdf = new SimpleDateFormat(env.getProperty("config.format"), Locale.ENGLISH);
		this.log = LoggerFactory.getLogger(getClass());
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

	@Override
	public CardDetail retrieveByUsrAndId(String user, String id) {
		BnkCxuCardUser response =  repo.findByCxuCodeAndCxuUsrcod(id, user);
		if(response != null)
			return parse.apply(response);
		return null;
	}

	@Override
	public CardDetail retrieveById(String id) {
		Optional<BnkCxuCardUser> response =  repo.findById(id);
		if(response.isPresent())
			return parse.apply(response.get());
		return null;
	}

	@Override
	public boolean operation(String prdId, double amount) {
		try {
			Optional<BnkCxuCardUser> obj = repo.findById(prdId);
			if(obj.isPresent()) {
				BnkCxuCardUser save = obj.get();
				save.setCxuAvailable(save.getCxuAvailable().add(new BigDecimal(amount)));
				
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
