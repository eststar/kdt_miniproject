package com.mini.service;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mini.domain.Members;
import com.mini.domain.Provider;
import com.mini.domain.Role;
import com.mini.dto.MemberDTO;
import com.mini.exception.NicknameDuplicateException;
import com.mini.exception.UsernameDuplicateException;
import com.mini.persistence.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memRepo;
	private PasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Transactional
	public MemberDTO save(String memeberID, String username, String provider, String password, String nickname) {
		String encodedPass = encoder.encode(password);
		Members member = memRepo.findById(memeberID)
								.map(mem->{mem.setNickname(nickname); return mem;})
								.orElseGet(()->memRepo.save(Members.builder()
											.memberId(memeberID)
											.username(username)
											.password(encodedPass)
											.role(Role.ROLE_MEMBER)
											.enabled(true)
											.provider(Provider.findByString(provider))
											.createDate(OffsetDateTime.now())
											.nickname(nickname)
											.build())
											);
		return MemberDTO.builder().memberId(member.getMemberId()).build();
	}
	
	public MemberDTO signUp(MemberDTO member) {
		if(memRepo.existsByUsername(member.getUsername()))
			throw new UsernameDuplicateException();
		if(memRepo.existsByNickname(member.getNickname()))
			throw new NicknameDuplicateException();
		
		String memberId = member.getProvider() + "_"+ member.getUsername();
		Members success = memRepo.save(Members.builder()
				.memberId(memberId)
				.username(member.getUsername())
				.password(encoder.encode(member.getPassword()))
				.role(Role.ROLE_MEMBER)
				.enabled(true)
				.provider(member.getProvider())
				.createDate(OffsetDateTime.now())
				.nickname(member.getNickname())
				.build());
		
		return MemberDTO.builder().username(success.getUsername()).nickname(success.getNickname()).build();
	}
	
	public Boolean isAlreadySignedUp(String memberId){
		return memRepo.existsById(memberId);		
	}
	
	public Optional<Members> findByMemberId(String memberId){
		return memRepo.findById(memberId);
	}
}
