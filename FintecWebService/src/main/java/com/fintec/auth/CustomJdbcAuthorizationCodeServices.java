package com.fintec.auth;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;

import com.fintec.oauth.model.OauthCode;
import com.fintec.repositories.OauthCodeRepository;

public class CustomJdbcAuthorizationCodeServices extends JdbcAuthorizationCodeServices {

	@Autowired
	private OauthCodeRepository codeRepository;
	
	private static final Log logger = LogFactory.getLog(CustomJdbcClientDetailsService.class);

	public CustomJdbcAuthorizationCodeServices(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	protected void store(String code, OAuth2Authentication authentication) {
		try {
			OauthCode oauthCode = new OauthCode();
			oauthCode.setCode(code);
			oauthCode.setAuthentication(SerializationUtils.serialize(authentication));

			codeRepository.save(oauthCode);
		} catch (Exception exception) {
			logger.error("Failed to insert record in Oauth code table : {}",exception);
		}
	}

	@Override
	public OAuth2Authentication remove(String code) {
		OAuth2Authentication auth2Authentication = null;
		try {
			auth2Authentication = SerializationUtils.deserialize(codeRepository.findOne(code).getAuthentication());

			if (auth2Authentication != null) {
				codeRepository.delete(code);
			}
		} catch (Exception exception) {
			logger.error("Failed to delete record in Oauth code table : {}",exception);
		}

		return auth2Authentication;
	}

}
