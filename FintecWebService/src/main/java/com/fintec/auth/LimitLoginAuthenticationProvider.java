package com.fintec.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.fintec.oauth.model.User;
import com.fintec.service.UserAttemptService;
import com.fintec.service.UserService;

public class LimitLoginAuthenticationProvider extends DaoAuthenticationProvider {

	@Autowired
	private UserService userService;
		
	@Autowired
	private UserAttemptService userAttemptService;

	@Override
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		super.setUserDetailsService(userDetailsService);
	}

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {

		try {
			Authentication auth = super.authenticate(authentication);
			
			User user = userService.getUser(authentication.getName());
			userAttemptService.resetFailAttempts(user);
			
			return auth;

		} catch (BadCredentialsException e) {
			User user = userService.getUser(authentication.getName());
			userAttemptService.updateFailAttempts(user);
			throw new BadCredentialsException("Incorrect Credentials!",e);
		} catch (LockedException e) {
			String error = "User Account is locked!";
			throw new LockedException(error,e);
		} catch (Exception e) {
			throw e;
		}

	}
}
