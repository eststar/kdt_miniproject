package com.mini.domain;


import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

public class SecurityUser extends User{
	private static final long serialVersionUID = 1L;
	
	//실제로 members entity의 pk로 사용되는 memberID가 securityContext에서 강제되는 username으로 사용되므로 안헷갈리게
	public String getMemberId() {
		return super.getUsername();
	}
	public Provider getProvider() {
		return member.getProvider();
	}

	@Getter
	private final Members member;
	
	public SecurityUser(Members member) {
		super(member.getMemberId(), member.getPassword(), 
				AuthorityUtils.createAuthorityList(member.getRole().toString()));
		this.member = member;
	}		
}
