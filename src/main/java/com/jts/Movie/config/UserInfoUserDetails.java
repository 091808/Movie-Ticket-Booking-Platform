package com.jts.Movie.config;

import java.util.Arrays;
import java.util.stream.Collectors;

public class UserInfoUserDetails {
	
	private static final long serialVersionUID = -8773921465190832995L;
	private String name;
	private String password;
	private List<GrantedAuthority> authorities;
	
	public UserInfoUserDetails(User userInfo) {
		name = userInfo.getEmailId();
		password = userInfo.getPassword();
		authorities = Arrays.stream(userInfo.getRoles().split(","))
				      .map(SimpleGrantedAuthority::new)
				      .collect(Collectors.toList());
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	@Override
	public String getUsername() {
		return name;
	}

}
