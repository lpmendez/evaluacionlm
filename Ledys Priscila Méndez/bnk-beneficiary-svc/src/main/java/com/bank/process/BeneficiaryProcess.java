package com.bank.process;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bank.data.beneficiary.IBeneficiaryData;
import com.bank.exception.ApplicationException;
import com.bank.pojo.input.Beneficiary;
import com.bank.process.product.IProductProcess;
import com.bank.utils.ResponseCode;
import com.bank.utils.ResponseMsg;

@Service
public class BeneficiaryProcess implements IBeneficiaryProcess {

	private ApplicationContext context;
	private IBeneficiaryData data;
	private Logger log;
	
	public BeneficiaryProcess(ApplicationContext context,
			@Qualifier("BeanDataBen") IBeneficiaryData data ) {
		this.context = context;
		this.data = data;
		this.log = LoggerFactory.getLogger(getClass());
	}
	@Override
	public void save(Beneficiary request) {

		try {
			
			//validar si viene toda la info necesaria
			if(isNullOrEmpty(request.getEmail()) || isNullOrEmpty(request.getName()) 
					|| isNullOrEmpty(request.getType()) || isNullOrEmpty(request.getUsr()) 
					|| isNullOrEmpty(request.getAccount())) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.NO_DATA)),
						ResponseCode.NO_DATA, ResponseMsg.NO_DATA);
			}

			//validar formato de email
			
		    if (!validateEmail(request.getEmail())) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.ERROR)),
						ResponseCode.ERROR, "Invalid email");
		    }
			
			
			// mandar a llamar al servicio correspondiente del servicio de producto, creo que lo haré igual que en transaction
	
			IProductProcess prodProcess =  (IProductProcess) context.getBean(request.getType());
			boolean ownProd= prodProcess.existsProd(request.getUsr(), request.getAccount());
			boolean exists= prodProcess.existsProd(request.getAccount());
	
			if(!exists) {
				// si no existe, responder con 404.
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.INVALID)),
						ResponseCode.INVALID, ResponseMsg.INVALID);
			}
			if(ownProd) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.ERROR)),
						ResponseCode.ERROR, "The owner of the product is the same user");
			}
		    
		    //validar que no esté asociada ya al usuario.
		    if(data.existsBy(request.getUsr(), request.getAccount())) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.DUPLICATED)),
						ResponseCode.DUPLICATED, ResponseMsg.DUPLICATED);
		    }
		    //sino guardarla
			if(!data.save(request))
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.ERROR)),
						ResponseCode.ERROR, "Error saving beneficiary");

		}

		catch(ApplicationException ex) {
			throw ex;
		}
		catch(Exception ex) {
			log.error("Error in process.save beneficiary. Error: {}. USer: {}", ex.getMessage(), request.getUsr(), ex);
		}
	}

	public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }
	public boolean validateEmail(String email) {
		String pattern = "^(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])$";
		
	    Pattern r = Pattern.compile(pattern);

	    Matcher m = r.matcher(email);

	    if (!m.find()) {
	    	return false;
	    }
	    return true;
	}
	@Override
	public void update(Beneficiary request) {

		try {
			
			//validar si viene toda la info necesaria
			if(isNullOrEmpty(request.getEmail()) || isNullOrEmpty(request.getId()) || 
					isNullOrEmpty(request.getUsr()) ) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.NO_DATA)),
						ResponseCode.NO_DATA, ResponseMsg.NO_DATA);
			}

			//validar formato de email
			
		    if (!validateEmail(request.getEmail())) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.ERROR)),
						ResponseCode.ERROR, "Invalid email");
		    }
			
		    //validar que SI esté asociada ya al usuario.
		    if(!data.existsByUsrAndId(request.getUsr(), request.getId())) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.INVALID)),
						ResponseCode.INVALID, "Beneficiary not found");
		    }
		    //sino actualizarla
			if(!data.update(request))
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.ERROR)),
						ResponseCode.ERROR, "Error updating beneficiary");

		}

		catch(ApplicationException ex) {
			throw ex;
		}
		catch(Exception ex) {
			log.error("Error in process.save beneficiary. Error: {}. USer: {}", ex.getMessage(), request.getUsr(), ex);
		}
	}
	@Override
	public void delete(String id, String usr) {
		try {
			
		    //validar que SI esté asociada ya al usuario.
		    if(!data.existsByUsrAndId(usr, id)) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.INVALID)),
						ResponseCode.INVALID, "Beneficiary not found");
		    }
		    //sino eliminarla
			if(!data.delete(id))
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.ERROR)),
						ResponseCode.ERROR, "Error deleting beneficiary");

		}

		catch(ApplicationException ex) {
			throw ex;
		}
		catch(Exception ex) {
			log.error("Error in process.save beneficiary. Error: {}. USer: {}", ex.getMessage(), usr, ex);
		}
		
	}
	@Override
	public Beneficiary retrieveByIdAndUsr(String id, String usr) {
		return data.findByIdAndUsr(id, usr);
	}
}
