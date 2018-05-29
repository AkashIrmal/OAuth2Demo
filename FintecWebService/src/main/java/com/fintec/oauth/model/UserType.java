package com.fintec.oauth.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="TbmstUserType")
@NoArgsConstructor
@RequiredArgsConstructor
public @Data class UserType implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USERTYPEID")
	@NonNull
	private Integer usertypeId;
		
	@Column(name = "CLIENTID")
	private Integer clientId;
		
	@Column(name = "USERTYPECODE")
	@NonNull
	private String userTypeCode;
	
	@Column(name = "USERTYPENAME")
	@NonNull
	private String userTypeName;
		
	@Column(name = "USERTYPELEVEL")
	@NonNull
	private Integer userTypeLevel;
		
	@Column(name = "ACTIVE")
	@NonNull
	private String active;
		

}
