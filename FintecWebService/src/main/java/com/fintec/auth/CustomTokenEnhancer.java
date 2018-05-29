package com.fintec.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Service;

@Service
public class CustomTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		try {
			UserPrincipalImpl UserPrincipalImpl = (UserPrincipalImpl) authentication.getPrincipal();
			final Map<String, Object> additionalInfo = new HashMap<String, Object>();

			additionalInfo.put("user", UserPrincipalImpl.getUser());

			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

			return accessToken;
		} catch (Exception exception) {
			exception.printStackTrace();
			return accessToken;
		}
	}
}
