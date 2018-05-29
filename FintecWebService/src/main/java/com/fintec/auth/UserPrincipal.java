package com.fintec.auth;

import org.springframework.security.core.userdetails.UserDetails;

import com.fintec.oauth.model.User;


public interface UserPrincipal extends UserDetails  {
	 public User getUser();
}
