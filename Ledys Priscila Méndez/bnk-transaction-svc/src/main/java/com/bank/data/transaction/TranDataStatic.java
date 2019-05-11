package com.bank.data.transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bank.pojo.output.TransactionDetail;

@Service("TranDataStatic")
public class TranDataStatic implements ITransactionData {

	private Map<String, List<TransactionDetail>> trans;
	
	public TranDataStatic() {
		trans = new HashMap<>();
		List<TransactionDetail> list = new ArrayList<>();
		list.add(new TransactionDetail("345634", "19-Oct-2018", "Compra adidas", 50.6));
		list.add(new TransactionDetail("23446", "20-Dec-2018", "Regalo navidad", 5.60));
		list.add(new TransactionDetail("645", "14-Feb-2019", "Regalo dia de amor y amistad", 18));
		list.add(new TransactionDetail("345", "01-Jan-2019", "Pago de prestamo", 150.6));
		
		trans.put("LPMENDEZ", list);
	}
	
	@Override
	public List<TransactionDetail> retrieveBy(String prdId, String usr, String start, String end) {
		
		return trans.get(usr);
	}

}
