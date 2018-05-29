package com.fintec.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fintec.oauth.model.UserAttempt;

public interface UserAttemptRepository extends CrudRepository<UserAttempt, Long> {
	public UserAttempt getByUserId(@Param("userId") Integer userId);
	
}
