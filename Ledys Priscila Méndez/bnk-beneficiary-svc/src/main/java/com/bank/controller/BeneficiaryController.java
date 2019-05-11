package com.bank.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bank.pojo.input.Beneficiary;
import com.bank.process.IBeneficiaryProcess;
import com.bank.utils.ResponseCode;

@RestController
public class BeneficiaryController {

	private IBeneficiaryProcess process;
	
	public BeneficiaryController(IBeneficiaryProcess process) {
		this.process = process;
	}
	@PostMapping("${config.endpoints.beneficiary}")
	public void save(HttpServletResponse response,
			@RequestHeader("user") String user,
			@RequestBody Beneficiary info) {
		info.setUsr(user);
		process.save(info);
		response.setStatus(Integer.parseInt(ResponseCode.SUCCESS));
	}
	

	@PatchMapping("${config.endpoints.beneficiary-id}")
	public void update(HttpServletResponse response,
			@RequestHeader("user") String user,
			@PathVariable("beneficiaryID") String beneficiaryID,
			@RequestBody Beneficiary info) {
		info.setUsr(user);
		info.setId(beneficiaryID);
		process.update(info);
		response.setStatus(Integer.parseInt(ResponseCode.UPDATED));
	}

	@DeleteMapping("${config.endpoints.beneficiary-id}")
	public void delete(HttpServletResponse response,
			@RequestHeader("user") String user,
			@PathVariable("beneficiaryID") String beneficiaryID) {
		process.delete(beneficiaryID, user);
		response.setStatus(Integer.parseInt(ResponseCode.UPDATED));
	}
}
