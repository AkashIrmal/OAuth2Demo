package com.fintec.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fintec.oauth.model.User;

public class UserPrincipalImpl implements UserPrincipal {

	private static final long serialVersionUID = 1L;
	private User user;
	private Collection<GrantedAuthority> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return user.getLoginPassword();
	}

	@Override
	public String getUsername() {
		return this.user.getLoginName();
	}

	@Override
	public boolean isAccountNonExpired() {
		//return AuthConstants.userType(this.user.getAccountNonExpired());
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		//return AuthConstants.userType(this.user.getAccountNonLocked());
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		//return AuthConstants.userType(this.user.getCredentialsNonExpired());
		return true;
	}

	@Override
	public boolean isEnabled() {
		//return AuthConstants.userEnabled(this.user.getActive());//return this.user.getActive().equalsIgnoreCase(AuthConstants.STATE_ACTIVE);
		return true;
	}

	@Override
	public User getUser() {
		return this.user;
	}

	@Override
	public boolean equals(Object otherUser) {
		UserPrincipalImpl lUser = (UserPrincipalImpl) otherUser;
		if (getUser().getUserId() == lUser.getUser().getUserId())
			return true;

		return false;
	}

	@Override
	public int hashCode() {
		return getUser().getUserId();
	}

	public void setUser(User user) {
		this.user = user;
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(AuthConstants.ROLE_ADMIN));
		authorities.add(new SimpleGrantedAuthority(AuthConstants.ROLE_ADMIN1));
		//authorities.add(new SimpleGrantedAuthority(String.valueOf(AuthConstants.ROLE_SECRETARY)));
		this.authorities=authorities;
	}
	
	/*public static void main(String[] args) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(6);
		
		System.out.println(Encryptors.delux("password", "68656c6c6f").encrypt("root"));
		System.out.println(bCryptPasswordEncoder.encode("secret"));
		System.out.println(bCryptPasswordEncoder.encode("Passw0rd"));
	}*/
}
