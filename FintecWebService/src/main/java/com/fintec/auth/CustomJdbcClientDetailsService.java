package com.fintec.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.fintec.oauth.model.OauthClientDetails;

public class CustomJdbcClientDetailsService  extends JdbcClientDetailsService{
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static final Log logger = LogFactory.getLog(CustomJdbcClientDetailsService.class);

	@Autowired
	private com.fintec.repositories.OauthClientDetailsRepository clientDetailsRepository;
	
	public CustomJdbcClientDetailsService(DataSource dataSource) {
		super(dataSource);
	}
	
	
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
		ClientDetails details;
		try {
			OauthClientDetails clientDetails = clientDetailsRepository.findOne(clientId);
			
			if(passwordEncoder.matches("Peri", clientDetails.getClientSecret())){
				System.out.println("...................++++++++++++++++++++"+true);
			}
			details = extractClientDetails(clientDetails);
		}
		catch (EmptyResultDataAccessException e) {
			throw new NoSuchClientException("No client with requested id: " + clientId);
		}

		return details;
	}

	
	@Override
	public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
		 JsonMapper mapper = createJsonMapper();
		try {
			OauthClientDetails oauthClientDetails = new OauthClientDetails();
			oauthClientDetails.setAccessTokenValidity(clientDetails.getAccessTokenValiditySeconds());
			oauthClientDetails.setAdditionalInformation(mapper.write(clientDetails.getAdditionalInformation()));
			oauthClientDetails.setAuthorities(clientDetails.getAuthorities() != null ? StringUtils.collectionToCommaDelimitedString(clientDetails.getAuthorities()) : null);
			oauthClientDetails.setAuthorizedGrantTypes(clientDetails.getAuthorizedGrantTypes() != null ? StringUtils.collectionToCommaDelimitedString(clientDetails.getAuthorizedGrantTypes()) : null);
			oauthClientDetails.setAutoApprove("Y");
			oauthClientDetails.setClientid(clientDetails.getClientId());
			oauthClientDetails.setClientSecret(passwordEncoder.encode(clientDetails.getClientSecret()));
			oauthClientDetails.setRefreshTokenValidity(clientDetails.getRefreshTokenValiditySeconds());
			oauthClientDetails.setResourceId(clientDetails.getResourceIds() != null ? StringUtils.collectionToCommaDelimitedString(clientDetails.getResourceIds()) : null);
			oauthClientDetails.setScope(clientDetails.getScope() != null ? StringUtils.collectionToCommaDelimitedString(clientDetails.getScope()) : null);
			oauthClientDetails.setWebServerRedirectUri(clientDetails.getRegisteredRedirectUri() != null ? StringUtils.collectionToCommaDelimitedString(clientDetails.getRegisteredRedirectUri()) : null);
			
			clientDetailsRepository.save(oauthClientDetails);
		} catch (Exception exception) {
			logger.error("Failed to insert record to client details table : {}", exception);
		}
	}

	
	@Override
	public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
		try{
		 JsonMapper mapper = createJsonMapper();
		OauthClientDetails oauthClientDetails = clientDetailsRepository.findOne(clientDetails.getClientId());
		if(oauthClientDetails != null){
			oauthClientDetails.setAccessTokenValidity(clientDetails.getAccessTokenValiditySeconds());
			oauthClientDetails.setAdditionalInformation(mapper.write(clientDetails.getAdditionalInformation()));
			oauthClientDetails.setAuthorities(clientDetails.getAuthorities() != null ? StringUtils.collectionToCommaDelimitedString(clientDetails.getAuthorities()) : null);
			oauthClientDetails.setAuthorizedGrantTypes(clientDetails.getAuthorizedGrantTypes() != null ? StringUtils.collectionToCommaDelimitedString(clientDetails.getAuthorizedGrantTypes()) : null);
			oauthClientDetails.setAutoApprove("Y");
			oauthClientDetails.setClientSecret(passwordEncoder.encode(clientDetails.getClientSecret()));
			oauthClientDetails.setRefreshTokenValidity(clientDetails.getRefreshTokenValiditySeconds());
			oauthClientDetails.setResourceId(clientDetails.getResourceIds() != null ? StringUtils.collectionToCommaDelimitedString(clientDetails.getResourceIds()) : null);
			oauthClientDetails.setScope(clientDetails.getScope() != null ? StringUtils.collectionToCommaDelimitedString(clientDetails.getScope()) : null);
			oauthClientDetails.setWebServerRedirectUri(clientDetails.getRegisteredRedirectUri() != null ? StringUtils.collectionToCommaDelimitedString(clientDetails.getRegisteredRedirectUri()) : null);
			
			clientDetailsRepository.save(oauthClientDetails);
		}else{
			logger.error("Client details not found while update client details");
		}
		}catch(Exception exception){
			logger.error("Failed to update record to client details table : {}", exception);
		}
	}
	
	
	@Override
	public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {

		try {
			OauthClientDetails oauthClientDetails = clientDetailsRepository.findOne(clientId);
			oauthClientDetails.setClientSecret(passwordEncoder.encode(secret));
		} catch (Exception exception) {
			// TODO Auto-generated catch block
			logger.error("Failed to update client secret to client details table : {}", exception);
		}
	}
	
	@Override
	public void removeClientDetails(String clientId) throws NoSuchClientException {

		try {
			clientDetailsRepository.delete(clientId);
		} catch (Exception exception) {
			logger.error("Failed to delete record to client details table : {}", exception);
		}
	}
	
	
	@Override
	public List<ClientDetails> listClientDetails() {
		List<ClientDetails> clientDetails = new ArrayList<ClientDetails>();
		List<OauthClientDetails> OauthClientDetails = clientDetailsRepository.findAll();
		OauthClientDetails.forEach(x->{
			clientDetails.add(extractClientDetails(x));
		});
		
		return clientDetails;
	}
	
	private ClientDetails extractClientDetails(OauthClientDetails clientDetails) {
		
		JsonMapper mapper = createJsonMapper();
		
		BaseClientDetails details = new BaseClientDetails(clientDetails.getClientid(), clientDetails.getResourceId(), clientDetails.getScope(),
				clientDetails.getAuthorizedGrantTypes(), clientDetails.getAuthorities(), clientDetails.getWebServerRedirectUri());
		details.setClientSecret(clientDetails.getClientSecret());
		if (clientDetails.getAccessTokenValidity()!= null) {
			details.setAccessTokenValiditySeconds(clientDetails.getAccessTokenValidity());
		}
		if (clientDetails.getRefreshTokenValidity() != null) {
			details.setRefreshTokenValiditySeconds(clientDetails.getRefreshTokenValidity());
		}
		String json = clientDetails.getAdditionalInformation();
		if (json != null) {
			try {
				@SuppressWarnings("unchecked")
				Map<String, Object> additionalInformation = mapper.read(json, Map.class);
				details.setAdditionalInformation(additionalInformation);
			}
			catch (Exception e) {
				logger.warn("Could not decode JSON for additional information: " + details, e);
			}
		}
		String scopes = clientDetails.getAutoApprove();
		if (scopes != null) {
			details.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(scopes));
		}
		return details;
	}
	
	
	interface JsonMapper {
		String write(Object input) throws Exception;

		<T> T read(String input, Class<T> type) throws Exception;
	}
	
	static JsonMapper createJsonMapper() {
		if (ClassUtils.isPresent("org.codehaus.jackson.map.ObjectMapper", null)) {
			return new JacksonMapper();
		}
		else if (ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", null)) {
			return new Jackson2Mapper();
		}
		return new NotSupportedJsonMapper();
	}
	
	private static class JacksonMapper implements JsonMapper {
		private org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();

		@Override
		public String write(Object input) throws Exception {
			return mapper.writeValueAsString(input);
		}

		@Override
		public <T> T read(String input, Class<T> type) throws Exception {
			return mapper.readValue(input, type);
		}
	}
	
	private static class Jackson2Mapper implements JsonMapper {
		private com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

		@Override
		public String write(Object input) throws Exception {
			return mapper.writeValueAsString(input);
		}

		@Override
		public <T> T read(String input, Class<T> type) throws Exception {
			return mapper.readValue(input, type);
		}
	}

	private static class NotSupportedJsonMapper implements JsonMapper {
		@Override
		public String write(Object input) throws Exception {
			throw new UnsupportedOperationException(
					"Neither Jackson 1 nor 2 is available so JSON conversion cannot be done");
		}

		@Override
		public <T> T read(String input, Class<T> type) throws Exception {
			throw new UnsupportedOperationException(
					"Neither Jackson 1 nor 2 is available so JSON conversion cannot be done");
		}
	}

}
