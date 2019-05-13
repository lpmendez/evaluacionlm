package com.bank.data.transaction;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.bank.pojo.output.TransactionSave;
import com.bank.repository.TranRepository;

@Service("TranDatabase")
public class TranDatabase implements ITransactionData {
	private Logger log;
	private TranRepository repo;
	private SimpleDateFormat svc;
	private SimpleDateFormat svcTime;
	static SimpleDateFormat otime;
	
	public TranDatabase(TranRepository repo,
			Environment env) {
		this.repo = repo;
		this.log = LoggerFactory.getLogger(getClass());
		svc = new SimpleDateFormat(env.getProperty("config.format"), Locale.ENGLISH);
		svcTime = new SimpleDateFormat(env.getProperty("config.format-time"), Locale.ENGLISH);
		otime = new SimpleDateFormat(env.getProperty("config.date.output-time"), Locale.ENGLISH);
	}
	@Override
	public List<TransactionDetail> retrieveBy(String prdId, String usr, String start, String end) {
		try {
			end = end+" 23:59:59";
			List<BnkTraTransaction> trans = repo.findBy(usr, prdId, svc.parse(start), svcTime.parse(end));
			
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
			res.setId(String.valueOf(t.getTraCode()));
			return res;
		}
	};

	@Override
	public TransactionSave save(TransactionSave save) {
		BnkTraTransaction obj = new BnkTraTransaction();
		obj.setTraAccid(save.getAccId());
		obj.setTraAmount(new BigDecimal(save.getAmount()));
		obj.setTraCreatedBy(save.getUsrCode());
		obj.setTraCreatedDate(new Date());
		obj.setTraDescription(save.getDescription());
		obj.setTraStatus("A");
		obj.setTraUsrcod(save.getUsrCode());
		
		BnkTraTransaction res =  repo.save(obj);
		TransactionSave response = new TransactionSave();
		response.setAccId(res.getTraAccid());
		response.setAmount(res.getTraAmount().doubleValue());
		response.setDate(otime.format(res.getTraCreatedDate()));
		response.setDescription(res.getTraDescription());
		response.setId(String.valueOf(res.getTraCode()));
		response.setUsrCode(res.getTraUsrcod());
		
		return response;
	}
}
