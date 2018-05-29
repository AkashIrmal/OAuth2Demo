package com.fintec.model;

import java.util.Date;

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

import org.hibernate.annotations.CreationTimestamp;

import com.fintec.utils.Constants.BankAccountType;
import com.fintec.utils.Constants.TransStatus;

import lombok.Data;

@Entity
@Table(name= "TbmstTransactionLog")
@Data
public class TransactionLog {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "TRANSLOGID")
	private Long transLogId;
	
	@OneToOne
	@JoinColumn(name = "TRANSACTIONID")
	private Transaction transaction;
	
	
	@Column(name ="TRANSTATUSID")
	@Enumerated(EnumType.ORDINAL)
	private TransStatus transStatus;
	
	
	@CreationTimestamp
	@Column(name ="INITIATEDATE")
	private Date initiateDate;
}
