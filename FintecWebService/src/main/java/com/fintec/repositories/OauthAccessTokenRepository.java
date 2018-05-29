package com.fintec.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.fintec.oauth.model.OauthAccessToken;



public interface OauthAccessTokenRepository extends JpaRepository<OauthAccessToken, Integer> {

	public OauthAccessToken findByTokenId(String tokenId);

	@Modifying
	@Transactional
	@Query(value = "delete from OauthAccessToken o where o.tokenId = ?1")
	void deleteByTokenId(String tokenId);

	@Modifying
	@Transactional
	@Query(value = "delete from OauthAccessToken o where o.refreshToken = ?1")
	public void deleteByRefreshToken(String refreshToken);

	public List<OauthAccessToken> findByClientId(String clientId);

	public List<OauthAccessToken> findByUserName(String userName);

	public List<OauthAccessToken> findByUserNameAndClientId(String userName, String clientId);

    public OauthAccessToken findByAuthenticationId(String authenticationId);
}
