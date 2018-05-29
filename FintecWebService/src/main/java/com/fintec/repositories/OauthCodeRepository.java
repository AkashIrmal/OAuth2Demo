package com.fintec.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fintec.oauth.model.OauthCode;

public interface OauthCodeRepository extends JpaRepository<OauthCode, String>{

	
}
