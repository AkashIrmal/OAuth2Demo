package com.fintec.oauth.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "TbmstUser")
@Data
@NoArgsConstructor
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USERID")
	@NonNull
	private Integer userId;

	@Column(name = "USERNAME")
	@NonNull
	private String userName;

	@Column(name = "NOTIFICATIONMOBILE")
	private String notificationMobile;

	@Column(name = "LOGINNAME")
	private String loginName;

	@Column(name = "LOGINPASSWORD")
	@NonNull
	private String loginPassword;

	@Column(name = "EMAILID")
	private String emailId;

	@Column(name = "PASSWORDEXPIRE", columnDefinition = "CHAR")
	private String passwordExpire;

	@Column(name = "PASSWORDCHGNXTTM", columnDefinition = "CHAR")
	private String passwordchgnxttm;

	@Column(name = "ATTEMPTFLAG", columnDefinition = "CHAR")
	private String attemptFlag;

	@Column(name = "LOGINATTEMPTS")
	private Integer loginAttempts;

	@Column(name = "ISADMIN", columnDefinition = "CHAR")
	@NonNull
	private String isAdmin;

	@Column(name = "ACTIVE", columnDefinition = "CHAR")
	@NonNull
	private String active;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USERTYPEID")
	private UserType userType;

	@Column(name = "ACCOUNTNONEXPIRED", columnDefinition = "CHAR")
	private String accountNonExpired;

	@Column(name = "ACCOUNTNONLOCKED", columnDefinition = "CHAR")
	private String accountNonLocked;

	@Column(name = "CREDENTIALSNONEXPIRED", columnDefinition = "CHAR")
	private String credentialsNonExpired;

	@Column(name = "CREATEDBY")
	private Integer CREATEDBY;

	@OneToOne(mappedBy = "userId")
	@JsonIgnore
	private UserAttempt loginAttempId;
}
