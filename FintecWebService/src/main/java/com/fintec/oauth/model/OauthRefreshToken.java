package com.fintec.oauth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TbtrnRefreshToken")
@Data
public class OauthRefreshToken {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name ="ID")
	private Integer id;
	
	@Column(name = "TOKENID")
	private String tokenId;

	@Column(name = "TOKEN", columnDefinition="LONGBLOB")
	private byte[] token;

	@Column(name = "AUTHENTICATION", columnDefinition="LONGBLOB")
	private byte[] authentication;
	
}
