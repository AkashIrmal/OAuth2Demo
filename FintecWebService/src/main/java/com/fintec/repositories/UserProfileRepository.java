package com.fintec.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fintec.model.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long>{

	UserProfile findByProfileIdentifire(String profileIdentifire);
	
}
