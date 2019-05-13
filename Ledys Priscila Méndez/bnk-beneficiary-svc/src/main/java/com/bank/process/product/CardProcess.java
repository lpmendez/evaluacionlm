package com.bank.process.product;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bank.data.product.IProductData;
import com.bank.pojo.product.CardDetail;

@Service("creditCard")
public class CardProcess implements IProductProcess {

	private IProductData<CardDetail> data;
	
	public CardProcess(@Qualifier("BeanCardData") IProductData<CardDetail> data) {
		this.data = data;
	}
	@Override
	public boolean existsProd(String usr, String prdId) {
		return data.retrieveByUsrAndId(usr, prdId) != null;
	}
	@Override
	public boolean existsProd(String prdId) {
		return data.retrieveById(prdId) != null;
	}

}
