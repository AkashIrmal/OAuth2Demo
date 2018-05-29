package com.fintec.configuration;



import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fintec.auth.CustomJdbcAuthorizationCodeServices;
import com.fintec.auth.CustomJdbcClientDetailsService;
import com.fintec.auth.CustomJdbcTokenStore;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.fintec")
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations(
				"classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations(
				"classpath:/META-INF/resources/webjars/");
	}

	
	@Bean
	public PageableHandlerMethodArgumentResolver pageableResolver() {
		return new PageableHandlerMethodArgumentResolver(sortResolver());
	}

	@Bean
	public SortHandlerMethodArgumentResolver sortResolver() {
		return new SortHandlerMethodArgumentResolver();
	}

	@Bean
	public JdbcClientDetailsService clientDetailsService() {
		CustomJdbcClientDetailsService clientDetailsService = new CustomJdbcClientDetailsService(dataSource);
		clientDetailsService.setPasswordEncoder(getPasswordEncoder());
		return clientDetailsService;
	}

	@Bean
	public JdbcTokenStore jdbcTokenStore() {
		CustomJdbcTokenStore jdbcTokenStore = new CustomJdbcTokenStore(dataSource);
		return jdbcTokenStore;
	}

	@Bean
	public JdbcAuthorizationCodeServices jdbcAuthorizationCodeServices() {
		CustomJdbcAuthorizationCodeServices jdbcAuthorizationCodeServices = new CustomJdbcAuthorizationCodeServices(dataSource);
		return jdbcAuthorizationCodeServices;
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder(){
		return new BCryptPasswordEncoder(6);
	} 

}
