package com.fintec.oauth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Table(name = "TbtrnCode")
@Data
public class OauthCode {

	@Id
	@Column(name = "CODE")
	private String code;
	
	@Column(name = "AUTHENTICATION")
	private byte[] authentication;
}
