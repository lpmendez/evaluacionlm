package com.bank.data.product.card;

import org.springframework.stereotype.Service;

import com.bank.data.product.IProductData;
import com.bank.pojo.product.CardDetail;

@Service("CardDataStatic")
public class CardDataStatic implements IProductData<CardDetail> {

	@Override
	public CardDetail retrieveByUsrAndId(String usr, String id) {
		
		
		return null;
	}

	@Override
	public CardDetail retrieveById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
