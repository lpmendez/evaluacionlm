package com.bank.data.card;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bank.data.IProductData;
import com.bank.pojo.output.detail.CardDetail;

@Service("CardDataStatic")
public class CardDataStatic implements IProductData<CardDetail> {

	@Override
	public CardDetail retrieveByUsrAndId(String user, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CardDetail> retrieveAllByUser(String user) {
		if("LPMENDEZ".equals(user)) {
			return new ArrayList<>();
		}
		return null;
	}

	@Override
	public CardDetail retrieveById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean operation(String prdId, double amount) {
		// TODO Auto-generated method stub
		return false;
	}


}
