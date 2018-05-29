package com.fintec.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.fintec.oauth.model.OauthRefreshToken;

public interface OauthRefreshTokenRepository extends JpaRepository<OauthRefreshToken, Integer> {

	OauthRefreshToken findByTokenId(String tokenId);
	
	@Modifying
	@Transactional
	@Query(value = "delete from OauthRefreshToken o where o.tokenId = ?1")
	void deleteByTokenId(String tokenId);
	
}
