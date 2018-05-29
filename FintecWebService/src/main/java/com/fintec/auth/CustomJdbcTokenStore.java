package com.fintec.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import com.fintec.oauth.model.OauthAccessToken;
import com.fintec.oauth.model.OauthRefreshToken;
import com.fintec.repositories.OauthAccessTokenRepository;
import com.fintec.repositories.OauthRefreshTokenRepository;

public class CustomJdbcTokenStore extends JdbcTokenStore {

	private static final Log logger = LogFactory.getLog(CustomJdbcTokenStore.class);

	@Autowired
	private OauthAccessTokenRepository accessTokenRepository;

	@Autowired
	private OauthRefreshTokenRepository oauthRefreshTokenRepository;

	private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

	public CustomJdbcTokenStore(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public void setAuthenticationKeyGenerator(AuthenticationKeyGenerator authenticationKeyGenerator) {
		this.authenticationKeyGenerator = authenticationKeyGenerator;
	}

	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		OAuth2AccessToken accessToken = null;
		String key = authenticationKeyGenerator.extractKey(authentication);
		try {

			OauthAccessToken oauthAccessToken = accessTokenRepository.findByAuthenticationId(key);
			if (oauthAccessToken != null) {
				accessToken = deserializeAccessToken(oauthAccessToken.getToken());
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("Failed to find access token for authentication " + authentication);
				}
			}
		} catch (IllegalArgumentException e) {
			logger.error("Could not extract access token for authentication " + authentication, e);
		}

		if (accessToken != null && !key.equals(authenticationKeyGenerator.extractKey(readAuthentication(accessToken.getValue())))) {
			this.removeAccessToken(accessToken.getValue());
			// Keep the store consistent (maybe the same user is represented by
			// this authentication but the details have
			// changed)
			this.storeAccessToken(accessToken, authentication);
		}
		return accessToken;
	}

	@Override
	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		try {
			String refreshToken = null;

			if (token.getRefreshToken() != null) {
				refreshToken = token.getRefreshToken().getValue();
			}

			if (this.readAccessToken(token.getValue()) != null) {
				removeAccessToken(token.getValue());
				accessTokenRepository.flush();
			}
			OauthAccessToken accessToken = new OauthAccessToken();
			accessToken.setTokenId(extractTokenKey(token.getValue()));
			accessToken.setToken(serializeAccessToken(token));
			accessToken.setAuthenticationId(authenticationKeyGenerator.extractKey(authentication));
			accessToken.setUserName(authentication.isClientOnly() ? null : authentication.getName());
			accessToken.setClientId(authentication.getOAuth2Request().getClientId());
			accessToken.setAuthentication(serializeAuthentication(authentication));
			accessToken.setRefreshToken(extractTokenKey(refreshToken));

			accessTokenRepository.save(accessToken);
			

		/*	OauthAccessToken accessToken = accessTokenRepository.findOne(authenticationKeyGenerator.extractKey(authentication));

			if(accessToken !=null && accessToken.getAuthentication() != null){
				accessTokenRepository.delete(accessToken.getAuthenticationId());
			}
				accessToken.setTokenId(extractTokenKey(token.getValue()));
				accessToken.setToken(serializeAccessToken(token));
				accessToken.setAuthenticationId(authenticationKeyGenerator.extractKey(authentication));
				accessToken.setUserName(authentication.isClientOnly() ? null : authentication.getName());
				accessToken.setClientId(authentication.getOAuth2Request().getClientId());
				accessToken.setAuthentication(serializeAuthentication(authentication));
				accessToken.setRefreshToken(extractTokenKey(refreshToken));
				accessTokenRepository.saveAndFlush(accessToken);*/
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.error("Failed to insert accesstoken details : {}", exception);
		}
	}

	@Override
	public OAuth2AccessToken readAccessToken(String tokenValue) {
		OAuth2AccessToken accessToken = null;

		try {

			OauthAccessToken oauthAccessToken = accessTokenRepository.findByTokenId(extractTokenKey(tokenValue));

			if (oauthAccessToken != null) {
				accessToken = deserializeAccessToken(oauthAccessToken.getToken());
			} else {
				if (logger.isInfoEnabled()) {
					logger.info("Failed to find access token for token " + tokenValue);
				}
			}

		} catch (IllegalArgumentException e) {
			logger.warn("Failed to deserialize access token for " + tokenValue, e);
			this.removeAccessToken(tokenValue);
		}

		return accessToken;
	}

	@Override
	public void removeAccessToken(OAuth2AccessToken token) {
		this.removeAccessToken(token.getValue());
	}

	@Transactional
	@Override
	public void removeAccessToken(String tokenValue) {
		accessTokenRepository.deleteByTokenId(extractTokenKey(tokenValue));
		accessTokenRepository.flush();
	}
	

	public void removeAccessToken(Integer id) {
		accessTokenRepository.delete(id);
		accessTokenRepository.flush();
	}

	@Override
	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
		return this.readAuthentication(token.getValue());
	}

	@Override
	public OAuth2Authentication readAuthentication(String token) {
		OAuth2Authentication authentication = null;

		try {
			OauthAccessToken oauthAccessToken = accessTokenRepository.findByTokenId(extractTokenKey(token));
			if (oauthAccessToken != null) {
				authentication = deserializeAuthentication(oauthAccessToken.getAuthentication());
			} else {
				logger.info("Failed to find access token for token " + token);
			}
		} catch (IllegalArgumentException e) {
			logger.warn("Failed to deserialize authentication for " + token, e);
			this.removeAccessToken(token);
		}
		return authentication;
	}

	@Override
	public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
		try {
			OauthRefreshToken oauthRefreshToken = new OauthRefreshToken();
			oauthRefreshToken.setTokenId(extractTokenKey(refreshToken.getValue()));
			oauthRefreshToken.setToken(serializeRefreshToken(refreshToken));
			oauthRefreshToken.setAuthentication(serializeAuthentication(authentication));

			oauthRefreshTokenRepository.save(oauthRefreshToken);
		} catch (Exception e) {
			logger.warn("Failed to insert refresh token " + e);
		}
	}

	@Override
	public OAuth2RefreshToken readRefreshToken(String token) {
		OAuth2RefreshToken refreshToken = null;

		try {

			OauthRefreshToken oauthRefreshToken = oauthRefreshTokenRepository.findByTokenId(extractTokenKey(token));
			if (oauthRefreshToken != null) {
				refreshToken = deserializeRefreshToken(oauthRefreshToken.getToken());
			} else {
				logger.info("Failed to find refresh token for token " + token);
			}
		} catch (IllegalArgumentException e) {
			logger.warn("Failed to deserialize refresh token for token " + token, e);
			this.removeRefreshToken(token);
		}

		return refreshToken;
	}

	@Override
	public void removeRefreshToken(OAuth2RefreshToken token) {
		removeRefreshToken(token.getValue());
	}

	@Override
	public void removeRefreshToken(String token) {
		oauthRefreshTokenRepository.deleteByTokenId(extractTokenKey(token));
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
		return this.readAuthenticationForRefreshToken(token.getValue());
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(String value) {
		OAuth2Authentication authentication = null;

		try {
			OauthRefreshToken oauthRefreshToken = oauthRefreshTokenRepository.findByTokenId(extractTokenKey(value));
			if (oauthRefreshToken != null) {
				authentication = deserializeAuthentication(oauthRefreshToken.getAuthentication());
			} else {
				if (logger.isInfoEnabled()) {
					logger.info("Failed to find access token for token " + value);
				}
			}

		} catch (IllegalArgumentException e) {
			logger.warn("Failed to deserialize access token for " + value, e);
			this.removeRefreshToken(value);
		}

		return authentication;
	}

	@Override
	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
		removeAccessTokenUsingRefreshToken(refreshToken.getValue());
	}

	@Override
	public void removeAccessTokenUsingRefreshToken(String refreshToken) {
		try {
			accessTokenRepository.deleteByRefreshToken(extractTokenKey(refreshToken));
		} catch (Exception exception) {
			logger.error("Failed to delete access token details by refresh token {}", exception);
		}
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>();

		try {
			List<OauthAccessToken> OauthAccessTokenList = accessTokenRepository.findByClientId(clientId);
			/*
			 * OauthAccessTokenList.forEach(x -> { try{
			 * accessTokens.add(deserializeAccessToken
			 * (x.getToken().getBytes())); }catch (Exception e) {
			 * logger.warn("Failed to deserialize access token for " + e);
			 * accessTokenRepository.delete(x.getAuthenticationId()); }
			 * 
			 * )};
			 */
			for (OauthAccessToken accessToken : OauthAccessTokenList) {
				try {
					accessTokens.add(deserializeAccessToken(accessToken.getToken()));
				} catch (Exception e) {
					logger.warn("Failed to deserialize access token for " + e);
					accessTokenRepository.delete(accessToken.getId());
				}
			}

		} catch (Exception e) {
			logger.info("Failed to find access token for clientId " + clientId);
		}

		accessTokens = removeNulls(accessTokens);
		return accessTokens;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByUserName(String userName) {

		List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>();

		try {
			List<OauthAccessToken> OauthAccessTokenList = accessTokenRepository.findByUserName(userName);
			for (OauthAccessToken accessToken : OauthAccessTokenList) {
				try {
					accessTokens.add(deserializeAccessToken(accessToken.getToken()));
				} catch (Exception e) {
					logger.warn("Failed to deserialize access token for " + e);
					accessTokenRepository.delete(accessToken.getId());
				}
			}
		} catch (Exception e) {
			if (logger.isInfoEnabled())
				logger.info("Failed to find access token for userName " + userName);
		}
		 accessTokens = removeNulls(accessTokens);
		return accessTokens;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
		List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>();

		try {
			List<OauthAccessToken> OauthAccessTokenList = accessTokenRepository.findByUserNameAndClientId(userName, clientId);

			for (OauthAccessToken accessToken : OauthAccessTokenList) {
				try {
					accessTokens.add(deserializeAccessToken(accessToken.getToken()));
				} catch (Exception e) {
					logger.warn("Failed to deserialize access token for " + e);
					accessTokenRepository.delete(accessToken.getId());
				}
			}
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Failed to find access token for clientId " + clientId + " and userName " + userName);
			}
		}
		 accessTokens = removeNulls(accessTokens);
		return accessTokens;

	}
	
	private List<OAuth2AccessToken> removeNulls(List<OAuth2AccessToken> accessTokens) {
		List<OAuth2AccessToken> tokens = new ArrayList<OAuth2AccessToken>();
		for (OAuth2AccessToken token : accessTokens) {
			if (token != null) {
				tokens.add(token);
			}
		}
		return tokens;
	}
}
