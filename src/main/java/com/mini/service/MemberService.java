package com.mini.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import com.mini.domain.Members;
import com.mini.domain.Provider;
import com.mini.domain.Role;
import com.mini.persistence.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memRepo;
	
	public void save(String username, String provider) {
		if(memRepo.existsById(username))
			return;
		memRepo.save(Members.builder()
							.memberId(username)
							.password("****")
							.role(Role.ROLE_MEMBER)
							.enabled(true)
							.provider(Provider.findByString(provider))
							.createDate(OffsetDateTime.now())
							.nickname("")
							.build());
	}
}
