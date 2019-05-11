package com.bank.process;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bank.exception.ApplicationException;
import com.bank.pojo.input.ProcessReq;
import com.bank.pojo.output.Transaction;
import com.bank.process.product.IProductProcess;
import com.bank.utils.ResponseCode;
import com.bank.utils.ResponseMsg;

@Service
public class TranProcess implements ITranProcess {

	private Logger log;
	private SimpleDateFormat sdf;
	private Environment env;
	private ApplicationContext context;
	private SimpleDateFormat otime;
	
	public TranProcess(Environment env, ApplicationContext context) {
		this.env = env;
		sdf = new SimpleDateFormat(env.getProperty("config.format"), Locale.ENGLISH);
		this.log = LoggerFactory.getLogger(getClass());
		this.context = context;
		otime = new SimpleDateFormat(env.getProperty("config.date.output-time"), Locale.ENGLISH);
	}
	@Override
	public Transaction getTransByPrdAndAccAndDates(ProcessReq input) {
		// logica que pide el ejercicio
		try {
			
			//máximo de rango de fecha: 3 meses: 400
			Date startDate = sdf.parse(input.getStartDate());
			Date endDate = sdf.parse(input.getEndDate());
			
			Calendar newDate = Calendar.getInstance();
			newDate.setTime(startDate);
			newDate.add(Calendar.DAY_OF_YEAR, Integer.parseInt(env.getProperty("config.exceed-days")));

			//fecha inicial mayor a la final?: 400
			if(newDate.getTime().before(endDate) || startDate.after(endDate)) {
				throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.ERROR)), 
						ResponseCode.ERROR, ResponseMsg.ERROR);
			}

			// validar que la cuenta exista y pertenezca al cliente..: 404
			//creo que lo validaré en cada process..
			IProductProcess prodProcess =  (IProductProcess) context.getBean(input.getType());
			//ordenar las transacciones
			Transaction tra= prodProcess.execute(input);
			
			if(tra != null && tra.getTransactions() != null) {
				tra.getTransactions().sort((t1,t2) -> {
					try {
						return otime.parse(t2.getDate()).compareTo(otime.parse(t1.getDate()));
					} catch (ParseException e) {
						return 0;
					}
				});
				
				return tra;	
			}
			
			
			
		}
		catch(ApplicationException ex) {
			throw ex;
		}
		catch(Exception ex) {
			log.error("Error in process.getTransByPrdAndAccAndDates. Error: {}. USer: {}", ex.getMessage(), input.getUser(), ex);
		}

		throw new ApplicationException(HttpStatus.valueOf(Integer.parseInt(ResponseCode.ERROR)), 
				ResponseCode.ERROR, ResponseMsg.ERROR);
	}

}
