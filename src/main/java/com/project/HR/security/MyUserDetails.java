package com.project.HR.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.HR.vo.Authority;
import com.project.HR.vo.User;

public class MyUserDetails implements UserDetails {
	private static final long serialVersionUID = 1L;
	private User user;

	public MyUserDetails(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
//        return Arrays.asList(authority);
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Authority authority : user.getFeatureList()) {
			SimpleGrantedAuthority feature = new SimpleGrantedAuthority(authority.getFeature());
			authorities.add(feature);
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
