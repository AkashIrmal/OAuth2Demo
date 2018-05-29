package com.fintec.auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fintec.oauth.model.User;

@Service
public class AuthenticationUserDetailsServices implements UserDetailsService {

	@Autowired
	private com.fintec.service.UserService userService;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UserPrincipalImpl userPrincipalImpl = new UserPrincipalImpl();
		try {
			User user = userService.getUser(userName);

			if (user == null) {
				throw new UsernameNotFoundException("User with login user name -" + userName + "is not found");
			} else {
				userPrincipalImpl.setUser(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userPrincipalImpl;
	}
}
