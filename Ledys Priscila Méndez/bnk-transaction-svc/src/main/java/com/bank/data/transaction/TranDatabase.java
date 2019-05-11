package com.bank.data.transaction;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bank.entity.BnkTraTransaction;
import com.bank.pojo.output.TransactionDetail;
import com.bank.repository.TranRepository;

@Service("TranDatabase")
public class TranDatabase implements ITransactionData {
	private Logger log;
	private TranRepository repo;
	private SimpleDateFormat svc;
	static SimpleDateFormat otime;
	
	public TranDatabase(TranRepository repo,
			Environment env) {
		this.repo = repo;
		this.log = LoggerFactory.getLogger(getClass());
		svc = new SimpleDateFormat(env.getProperty("config.format"), Locale.ENGLISH);
		otime = new SimpleDateFormat(env.getProperty("config.date.output-time"), Locale.ENGLISH);
	}
	@Override
	public List<TransactionDetail> retrieveBy(String prdId, String usr, String start, String end) {
		try {
			List<BnkTraTransaction> trans = repo.findBy(usr, prdId, svc.parse(start), svc.parse(end));
			
			return
					trans.stream().map(parse).collect(Collectors.toList());
			
		} catch (Exception e) {
			log.error("Error in retrieve transaction database. Error: {}. User: {}", e.getMessage(), usr,e);
		}
		
		return null;
	}

	static Function<BnkTraTransaction, TransactionDetail> parse = new Function<BnkTraTransaction, TransactionDetail>() {

		@Override
		public TransactionDetail apply(BnkTraTransaction t) {
			TransactionDetail res = new TransactionDetail();
			res.setAmount(t.getTraAmount().doubleValue());
			res.setDate(otime.format(t.getTraCreatedDate()));
			res.setDescription(t.getTraDescription());
			res.setId(t.getTraCode());
			return res;
		}
	};
}
