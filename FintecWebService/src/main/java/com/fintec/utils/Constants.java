package com.fintec.utils;

import com.fasterxml.jackson.annotation.JsonValue;

public class Constants {

	public enum TransStatus{
		PROCCESS("Transaction in-process"),
		FAILED("Transaction failed"),
		SUCCESS("Transaction success");
		
		private String transStatus;
		
		private TransStatus(String transStatus){
			this.transStatus = transStatus;
		}
		
		@JsonValue
		private String getBankAccountType(){
			return this.transStatus;
		}
	}
	
	public enum ConstantActive{
		 YES,NO;
	}
	
	public enum BankAccountType{
		SAVING("Saving Account"),
		CURRENT("Current Account"),
		LOAN("Loan Account"),
		CASHCREDIT("Cash Credit");
		
		private String accountType;
		
		private BankAccountType(String accountType){
			this.accountType = accountType;
		}
		
		@JsonValue
		private String getBankAccountType(){
			return this.accountType;
		}
		
	}
	
	public enum TransactionType{
		
		CREDIT("Credit"),
		DEBIT("Debit");
		
		private String transactionType;
		
		private TransactionType(String transactionType){
			this.transactionType = transactionType;
		}
		
		@JsonValue
		private String getTransactionType(){
			return this.transactionType;
		}
	}
}
