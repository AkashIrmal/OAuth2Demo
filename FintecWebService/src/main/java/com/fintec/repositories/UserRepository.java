package com.fintec.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fintec.oauth.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	public User getByLoginName(@Param("loginName") String loginName);
}
