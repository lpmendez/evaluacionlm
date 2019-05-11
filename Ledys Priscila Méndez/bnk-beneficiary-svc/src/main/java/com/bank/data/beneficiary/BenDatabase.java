package com.bank.data.beneficiary;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bank.entity.BnkBenBeneficiary;
import com.bank.pojo.input.Beneficiary;
import com.bank.repository.BeneficiaryRepository;

@Service("BenDatabase")
public class BenDatabase implements IBeneficiaryData {

	private BeneficiaryRepository repo;
	private Logger log;
	
	public BenDatabase(BeneficiaryRepository repo) {
		this.repo = repo;
		this.log = LoggerFactory.getLogger(getClass());
	}
	
	@Override
	public boolean save(Beneficiary b) {
		try {
			//si ya existe inactiva.. si se borró pues..
			BnkBenBeneficiary input = repo.findByBenUsrcodAndBenAccountAndBenStatus(b.getUsr(), b.getAccount(), "I");
			if(input != null) {
				//cambiarle el status
				input.setBenEmail(b.getEmail());
				input.setBenModifiedBy(b.getUsr());
				input.setBenModifiedDate(new Date());
				input.setBenStatus("A");
			}
			else {
				// si no existe, creala
				input = new BnkBenBeneficiary();
				input.setBenAccount(b.getAccount());
				input.setBenCreatedBy(b.getUsr());
				input.setBenCreatedDate(new Date());
				input.setBenEmail(b.getEmail());
				input.setBenName(b.getName());
				input.setBenStatus("A");
				input.setBenType(b.getType());
				input.setBenUsrcod(b.getUsr());
			}
			repo.save(input);
			return true;
		}
		catch(Exception ex) {
			log.error("Error in data save beneficiary. Error: {}. USer: {}", ex.getMessage(), b.getUsr(), ex);
		}
		
		return false;
	}

	@Override
	public boolean update(Beneficiary b) {
		
		try {
			
			Optional<BnkBenBeneficiary> obj = repo.findById(Integer.parseInt(b.getId()));
			if(!obj.isPresent())
				return false;
		
			BnkBenBeneficiary info = obj.get();
			info.setBenModifiedDate(new Date());
			info.setBenModifiedBy(b.getUsr());
			info.setBenEmail(b.getEmail());
			return true;

		}
		catch(Exception ex) {
			log.error("Error in data update beneficiary. Error: {}. USer: {}. Beneficiary id: {}", 
					ex.getMessage(), b.getUsr(), b.getId(),ex);
		}
		return false;
	}

	@Override
	public boolean delete(String id) {
		try {
			Optional<BnkBenBeneficiary> obj = repo.findById(Integer.parseInt(id));
			if(!obj.isPresent())
				return false;
			
			BnkBenBeneficiary info = obj.get();
			info.setBenModifiedDate(new Date());
			info.setBenStatus("I");
			
			return true;
		}
		catch(Exception ex) {
			log.error("Error in data delete beneficiary. Error: {}. Beneficiary id: {}", ex.getMessage(), id, ex);
		}
		return false;
	}

	@Override
	public boolean exists(String id) {
		return repo.existsById(Integer.parseInt(id));
	}

	@Override
	public boolean existsBy(String usr, String account) {
		return repo.existsByBenUsrcodAndBenAccountAndBenStatus(usr, account, "A");
	}

}
