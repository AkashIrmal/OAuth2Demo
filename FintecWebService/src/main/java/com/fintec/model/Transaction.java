package com.fintec.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Table(name = "TbtrnTransaction")
@Data
public class Transaction {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="TRANSACTIONID")
	private Long transactionId;
	
	@CreationTimestamp
	@Column(name ="INITIATEDATE")
	private Date initiateDate;

}
