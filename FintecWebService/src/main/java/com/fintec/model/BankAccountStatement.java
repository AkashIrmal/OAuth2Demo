package com.fintec.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fintec.utils.Constants.TransactionType;

@Entity
@Table(name = "BankAccountStatement")
public class BankAccountStatement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BANKACCSTATEMENTID")
	private Long bankAccStatementId;

	@OneToOne
	@JoinColumn(name = "BANKACCOUNTID")
	private BankAccount bankAccount;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "EXECUATEDATE")
	private Date execuateDate;

	@Column(name = "AMOUNT")
	private Double amount;

	@Column(name = "TRANSACTIONTYPE")
	private TransactionType transactionType;
	

}
