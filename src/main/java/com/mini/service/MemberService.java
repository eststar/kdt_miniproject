package com.mini.service;

import java.time.OffsetDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	private PasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public void save(String memeberID, String username, String provider, String password) {
		if(memRepo.existsById(username))
			return;
		
		memRepo.save(Members.builder()
							.memberId(memeberID)
							.username(username)
							.password(encoder.encode(password))
							.role(Role.ROLE_MEMBER)
							.enabled(true)
							.provider(Provider.findByString(provider))
							.createDate(OffsetDateTime.now())
							.nickname("")
							.build());
	}
}
