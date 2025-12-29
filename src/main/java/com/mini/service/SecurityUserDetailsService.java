package com.mini.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mini.domain.Members;
import com.mini.domain.Provider;
import com.mini.domain.SecurityUser;
import com.mini.persistence.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService{
	
	private final MemberRepository memRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Members member = memRepo.findByUsernameAndProvider(username, Provider.LOCAL)
							.orElseThrow(()->new UsernameNotFoundException(username + " Not Found!"));
		
		return new SecurityUser(member);
	}
	
}
