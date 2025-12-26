package com.mini.domain;


import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

public class SecurityUser extends User{
	private static final long serialVersionUID = 1L;
	
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
