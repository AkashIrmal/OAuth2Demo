package com.fintec.service;

import com.fintec.oauth.model.User;




public interface UserAttemptService {

	public void resetFailAttempts(User user);
	public void updateFailAttempts(User user);


}
