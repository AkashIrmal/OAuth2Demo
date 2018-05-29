package com.fintec.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Value("${oauth.realm}")
	private static String REALM;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Autowired
	JdbcClientDetailsService clientDetailsService;

	@Autowired
	JdbcTokenStore jdbcTokenStore;

	@Autowired
	JdbcAuthorizationCodeServices jdbcAuthorizationCodeServices;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientDetailsService);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		endpoints
		.tokenStore(jdbcTokenStore)
		.authorizationCodeServices(jdbcAuthorizationCodeServices)
		.authenticationManager(authenticationManager)
		.approvalStoreDisabled()
		.tokenEnhancer(customeTokenEnhancer())
		.accessTokenConverter(new DefaultAccessTokenConverter());
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.realm(REALM + "/client").tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()").passwordEncoder(passwordEncoder);
	}

	@Bean
	public TokenEnhancer customeTokenEnhancer() {
		return new CustomTokenEnhancer();
	}
	
	@Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
