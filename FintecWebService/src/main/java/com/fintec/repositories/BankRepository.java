package com.fintec.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fintec.model.BankAccount;
import com.fintec.utils.Constants.ConstantActive;


@Repository
public interface BankRepository extends JpaRepository<BankAccount, Integer> {

	List<BankAccount> findByUserProfileProfileIdentifireAndActiveAndIsUseraccount(String profileIdentifire,ConstantActive active,char isUseraccount);
	BankAccount findByUserProfileProfileIdentifireAndAccountNumberAndIfsc(String profileIdentifire, String accountNumber, String ifsc);

}
