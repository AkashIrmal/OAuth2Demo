package com.fintec.utils;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fintec.auth.UserPrincipal;
import com.fintec.oauth.model.User;

public class CommonUtils {

	public static User getUserObjectFromSecurityContext() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
		return userPrincipal.getUser();
	}

	public static String getUniqueUUIDForBankAccount() {
		return UUID.randomUUID().toString();
	}

}
