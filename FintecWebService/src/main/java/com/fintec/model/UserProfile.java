package com.fintec.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "UserProfile")
@Data
public class UserProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USERPROFILEID")
	private Long userProfileId;

	@Column(name = "PROFILEIDENTIFIRE")
	String profileIdentifire;
}
