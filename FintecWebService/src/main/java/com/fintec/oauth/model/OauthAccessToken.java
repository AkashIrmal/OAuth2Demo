package com.fintec.oauth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TbtrnAccessToken")
@Data
public class OauthAccessToken {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	
	@Column(name = "AUTHENTICATIONID")
	private String authenticationId;

	@Column(name = "TOKENID")
	private String tokenId;

	@Column(name = "TOKEN", columnDefinition="LONGBLOB")
	private byte[] token;

	@Column(name = "USERNAME")
	private String userName;

	@Column(name = "CLIENTID")
	private String clientId;

	@Column(name = "AUTHENTICATION", columnDefinition="LONGBLOB")
	private byte[] authentication;

	@Column(name = "REFRESHTOKEN")
	private String refreshToken;

}
