package com.fintec.oauth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TbtrnClientDetails")
@Data
public class OauthClientDetails {
	@Id 
	@Column(name = "CLIENTID")
	private String clientid;

	@Column(name = "RESOURCEIDS")
	private String resourceId;
	
	@Column(name = "CLIENTSECRET")
	private String clientSecret;

	@Column(name = "SCOPE")
	private String scope;

	@Column(name = "AUTHORIZEDGRANTTYPES")
	private String authorizedGrantTypes;

	@Column(name = "WEBSERVERREDIRECTURI")
	private String webServerRedirectUri;

	@Column(name = "AUTHORITIES")
	private String authorities;

	@Column(name = "ACCESSTOKENVALIDITY")
	private Integer accessTokenValidity;
	
	@Column(name = "REFRESHTOKENVALIDITY")
	private Integer refreshTokenValidity;
	
	@Column(name = "ADDITIONALINFORMATION")
	private String additionalInformation;
	
	@Column(name = "AUTOAPPROVE")
	private String autoApprove;

}
