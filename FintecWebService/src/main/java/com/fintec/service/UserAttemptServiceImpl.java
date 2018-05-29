package com.fintec.service;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Service;

import com.fintec.oauth.model.User;
import com.fintec.oauth.model.UserAttempt;
import com.fintec.repositories.UserAttemptRepository;
import com.fintec.repositories.UserRepository;

@Service
public class UserAttemptServiceImpl implements UserAttemptService{

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserAttemptRepository userAttemptRepository;
	
	private static final int MAX_ATTEMPTS = 3;
	
	@Override
	public void resetFailAttempts(User user) {
		if(user.getLoginAttempId() != null){
			UserAttempt userAttempt = new UserAttempt();
			userAttempt.setLoginAttempId(user.getLoginAttempId().getLoginAttempId());
			userAttempt.setUserId(user);
			userAttempt.setAttempts(0);
			Date date= new Date();
			userAttempt.setLastmodified(new Timestamp(date.getTime()));
			userAttemptRepository.save(userAttempt);
		}
	}

	@Override
	public void updateFailAttempts(User user) {
		UserAttempt userAttempt = new UserAttempt();
		if(user.getLoginAttempId() != null){
			if((user.getLoginAttempId().getAttempts() + 1) >= MAX_ATTEMPTS){
				
				userAttempt.setLoginAttempId(user.getLoginAttempId().getLoginAttempId());
				userAttempt.setUserId(user);
				userAttempt.setAttempts(0);
				Date date= new Date();
				userAttempt.setLastmodified(new Timestamp(date.getTime()));
				userAttemptRepository.save(userAttempt);
				user.setAccountNonLocked("Y");
				userRepository.save(user);
				//return new ResponseEntity<String>(HttpStatus.LOCKED);
				throw new LockedException("User Account is locked!");
				
				
			}else{
				userAttempt.setLoginAttempId(user.getLoginAttempId().getLoginAttempId());
				userAttempt.setUserId(user);
				userAttempt.setAttempts(user.getLoginAttempId().getAttempts() + 1);
				Date date= new Date();
				userAttempt.setLastmodified(new Timestamp(date.getTime()));
				userAttemptRepository.save(userAttempt);
			}
				
		}else{
			userAttempt.setUserId(user);
			userAttempt.setAttempts(1);
			Date date= new Date();
			userAttempt.setLastmodified(new Timestamp(date.getTime()));
			userAttemptRepository.save(userAttempt);
		}
		
	}

}
