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
		// TODO Auto-generated method stub
		return super.getAuthorities();
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return super.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return super.getUsername();
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
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
