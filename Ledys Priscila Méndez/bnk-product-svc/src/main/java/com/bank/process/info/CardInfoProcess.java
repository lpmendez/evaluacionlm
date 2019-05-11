package com.bank.process.info;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bank.data.IProductData;
import com.bank.pojo.output.detail.CardDetail;

@Service
public class CardInfoProcess implements IInfoProcess<CardDetail> {

	private IProductData<CardDetail> data;
	
	public CardInfoProcess(@Qualifier("BeanCardData") IProductData<CardDetail> data) {
		this.data = data;
	}
	
	@Override
	public CardDetail retrieveBy(String usr, String id) {
		
		return data.retrieveByUsrAndId(usr, id);
	}

	@Override
	public CardDetail retrieveById(String id) {
		return data.retrieveById(id);
	}

}
