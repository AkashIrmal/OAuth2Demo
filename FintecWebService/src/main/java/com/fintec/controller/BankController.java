package com.fintec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fintec.model.BankAccount;
import com.fintec.service.BankService;
import com.fintec.utils.BaseResponse;

@RestController
@RequestMapping(value="/bank/")
public class BankController {

	@Autowired
	private BankService bankService;
	
	
	@RequestMapping(value="addBankAccount",method= RequestMethod.POST)
	public ResponseEntity<BaseResponse<Boolean>> addBankAccount(@RequestBody BankAccount bankAccount){
		bankAccount.setIsUseraccount('Y');
		return new ResponseEntity<BaseResponse<Boolean>>(bankService.addUserBankAccount(bankAccount),HttpStatus.OK);
	}
	
	@RequestMapping(value = "getBankAccounts", method=RequestMethod.POST)
	public ResponseEntity<BaseResponse<List<BankAccount>>> getBankAccounts(){
		return new ResponseEntity<BaseResponse<List<BankAccount>>>(bankService.getBankAccountByUser('Y'),HttpStatus.OK);
	}
	
	@RequestMapping(value = "addBeneficiaryBankAccount", method=RequestMethod.POST)
	public ResponseEntity<BaseResponse<Boolean>> addBeneficiaryBankAccount(@RequestBody BankAccount bankAccount){
		bankAccount.setIsUseraccount('N');
		return new ResponseEntity<BaseResponse<Boolean>>(bankService.addUserBankAccount(bankAccount),HttpStatus.OK);		
	}
	
	@RequestMapping(value = "getBeneficiaryBankAccounts", method=RequestMethod.POST)
	public ResponseEntity<BaseResponse<List<BankAccount>>> getBeneficiaryBankAccounts(){
		return new ResponseEntity<BaseResponse<List<BankAccount>>>(bankService.getBankAccountByUser('N'),HttpStatus.OK);
	}
	
}
