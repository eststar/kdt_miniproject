package com.mini.domain;


import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

public class SecurityUser extends User{
	private static final long serialVersionUID = 1L;
	
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return super.getAuthorities();
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

	public String getMemberId() {
		return super.getUsername();
	}

	@Override
	public boolean isEnabled() {
		return super.isEnabled();
	}

	@Getter
	private final Provider provider;
	@Getter
	private final String nickname;
	
	public SecurityUser(Members member) {
		super(member.getMemberId(), member.getPassword(), 
				AuthorityUtils.createAuthorityList(member.getRole().toString()));
		provider = member.getProvider();
		nickname = member.getNickname();
	}		
}
