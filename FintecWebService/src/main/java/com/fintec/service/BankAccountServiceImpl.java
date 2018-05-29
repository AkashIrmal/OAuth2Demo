package com.fintec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintec.model.BankAccount;
import com.fintec.model.UserProfile;
import com.fintec.oauth.model.User;
import com.fintec.repositories.BankRepository;
import com.fintec.repositories.UserProfileRepository;
import com.fintec.utils.BaseResponse;
import com.fintec.utils.CommonUtils;
import com.fintec.utils.Constants.ConstantActive;

@Service
public class BankAccountServiceImpl implements BankService {

	@Autowired
	private BankRepository bankRepository;


	@Autowired
	private UserProfileRepository userProfileRepository;

	@Override
	public BaseResponse<Boolean> addUserBankAccount(BankAccount bankAccount) {
		BaseResponse<Boolean> baseResponse = new BaseResponse<Boolean>();
		try {
			User user = CommonUtils.getUserObjectFromSecurityContext();
			BankAccount isBankAccount = bankRepository.findByUserProfileProfileIdentifireAndAccountNumberAndIfsc(user.getUserId().toString(), bankAccount.getAccountNumber(), bankAccount.getIfsc());

			if (isBankAccount != null) {
				baseResponse.setRecordAlreadyExistsException();
				return baseResponse;
			}
			bankAccount.setUserProfile(getUserProfile());
			bankAccount.setBankIdentifire(CommonUtils.getUniqueUUIDForBankAccount());
			bankAccount.setActive(ConstantActive.YES);
			BankAccount account = bankRepository.save(bankAccount);
			if (account == null) {
				baseResponse.setServiceExceptionResponse();
			} else {
				baseResponse.setSuccessResponse(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			baseResponse.setServiceExceptionResponse();
		}
		return baseResponse;
	}

	@Override
	public BaseResponse<List<BankAccount>> getBankAccountByUser(char isUserAccout) {
		BaseResponse<List<BankAccount>> baseResponse = new BaseResponse<List<BankAccount>>();
		try {
			List<BankAccount> bankAccounts = bankRepository.findByUserProfileProfileIdentifireAndActiveAndIsUseraccount(CommonUtils.getUserObjectFromSecurityContext().getUserId().toString(), ConstantActive.YES, isUserAccout);
			if (bankAccounts == null || bankAccounts.isEmpty()) {
				baseResponse.setEmptyResponse();
			} else {
				baseResponse.setSuccessResponse(bankAccounts);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			baseResponse.setServiceExceptionResponse();
		}

		return baseResponse;
	}

	@Override
	public UserProfile getUserProfile() {
		String userId = CommonUtils.getUserObjectFromSecurityContext().getUserId().toString();
		UserProfile userProfile = userProfileRepository.findByProfileIdentifire(userId);

		if (userProfile == null) {
			userProfile = new UserProfile();
			userProfile.setProfileIdentifire(userId);
		}
		return userProfile;
	}
}
