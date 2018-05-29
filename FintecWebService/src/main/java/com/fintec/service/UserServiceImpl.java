package com.fintec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintec.oauth.model.User;
import com.fintec.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	public User getUser(String loginName) {
		return userRepository.getByLoginName(loginName);
	}
}
