package com.fintec.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class OAuth2SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private AuthenticationUserDetailsServices userDetailsService;
	
	//private UserDetailsService userDetailsService;
	
    @Autowired
    private PasswordEncoder passwordEncoder; 

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.
		ignoring().
		antMatchers("/getLanguage").
		antMatchers("/forgotPassword").
		antMatchers("/forgotLoginName").
		antMatchers("/swagger-ui.html").
		antMatchers("/webjars/**").
		antMatchers("/swagger-resources/**").
		antMatchers("/v2/api-docs/**").
		antMatchers("/logout").
		antMatchers("/loadSecurityQuestionByUserId").
		antMatchers("/validateSecurityQuestion").
		antMatchers("/getLanguageList").
		antMatchers("/getLabels")
		.antMatchers("/saveLabels").
		antMatchers("/saveUser");
	}

	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().anonymous().disable().authorizeRequests().antMatchers("/oauth/check_token").permitAll().antMatchers("/oauth/token").permitAll();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
