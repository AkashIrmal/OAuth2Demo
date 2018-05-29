package com.fintec.service;

import java.util.List;

import com.fintec.model.BankAccount;
import com.fintec.model.UserProfile;
import com.fintec.utils.BaseResponse;

public interface BankService {

	public BaseResponse<Boolean> addUserBankAccount(BankAccount bankAccount);
	public BaseResponse<List<BankAccount>> getBankAccountByUser(char isUserAccount);
	public UserProfile getUserProfile();
	
}
