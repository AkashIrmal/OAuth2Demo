package com.fintec.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fintec.oauth.model.User;
import com.fintec.utils.Constants.BankAccountType;
import com.fintec.utils.Constants.ConstantActive;

import lombok.Data;

@Entity
@Table(name = "UserBankAccount")
@Data
public class BankAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BANKACCOUNTID")
	private Integer bankAccountId;

	@Column(name = "ACCOUNTHOLDERNAME")
	private String accountHolderName;

	@Column(name = "ACCOUNTNUMBER")
	private String accountNumber;

	@Column(name = "ACCOUNTTYPE")
	@Enumerated(EnumType.ORDINAL)
	private BankAccountType accountType;

	@Column(name = "BANKDETAILS")
	private String bankDetails;

	@Column(name = "BANKIDENTIFIRE")
	private String bankIdentifire;

	@Column(name = "ROUTINGNUMBER")
	private String routingNumber;

	@Column(name = "ACTIVE")
	@Enumerated(EnumType.ORDINAL)
	private ConstantActive active;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "USERPROFILEID")
	private UserProfile userProfile;

	@Column(name = "IFSC")
	private String ifsc;

	@Column(name = "ISUSERACCOUNT", columnDefinition = "char default 'N'")
	private char isUseraccount;

}
