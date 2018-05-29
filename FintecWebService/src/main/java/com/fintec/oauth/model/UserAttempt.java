package com.fintec.oauth.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "TbtrnUserAttempts")
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public @Data class UserAttempt implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LOGINATTEMPID")
	@NonNull
	private Integer loginAttempId;

	@JoinColumn(name = "USERID")
	@OneToOne
	@NonNull
	private User userId;

	@Column(name = "ATTEMPTS")
	@NonNull
	private Integer attempts;

	@Column(name = "LASTMODIFIED")
	@NonNull
	private Timestamp lastmodified;
}
