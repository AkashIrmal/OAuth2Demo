package com.fintec.service;

import com.fintec.oauth.model.User;

public interface UserService {
	
	public User getUser(String loginName);
}