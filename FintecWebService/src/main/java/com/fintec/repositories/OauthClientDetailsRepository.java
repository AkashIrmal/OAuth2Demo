package com.fintec.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fintec.oauth.model.OauthClientDetails;

public interface OauthClientDetailsRepository extends JpaRepository<OauthClientDetails, String> {

}
