package com.fintec.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends
		ResourceServerConfigurerAdapter {

	@Value("${resource.id}")
	private String RESOURCE_ID;;



	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.anonymous()
				.disable()
				.requestMatchers()
				.antMatchers("/**")
				.and()
				.authorizeRequests()
				/* .antMatchers("/**").permitAll() */
				/*
				 * .antMatchers("/**").access(
				 * "hasRole('CONFIGURATION_VIEW') and hasRole('DEFAULT') and hasRole('USER_DELETE') and hasRole('USER_EDIT') and hasRole('USER_GROUP_DELETE') and hasRole('USER_GROUP_EDIT') and hasRole('USER_GROUP_VIEW') and hasRole('USER_VIEW')"
				 * )
				 */
				.antMatchers("/**")
				.access("hasRole('ADMIN') or hasRole('SECRETARY') or hasRole('DOCTOR') or hasRole('EMPLOYEE') "
						+ "or hasRole('CONSUMER')"
						+ " or hasRole('SUPPORTADMIN')").and()
				.exceptionHandling()
				.accessDeniedHandler(new OAuth2AccessDeniedHandler()).and()
				.headers().frameOptions().disable();
	}
}