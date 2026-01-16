package com.mini.service;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mini.domain.Members;
import com.mini.domain.Provider;
import com.mini.domain.Role;
import com.mini.domain.SecurityUser;
import com.mini.dto.MemberDTO;
import com.mini.dto.MemberDeleteDTO;
import com.mini.dto.MemberReqDTO;
import com.mini.dto.MemberRespDTO;
import com.mini.exception.NicknameDuplicateException;
import com.mini.exception.UsernameDuplicateException;
import com.mini.persistence.BoardRepository;
import com.mini.persistence.CommentRepository;
import com.mini.persistence.MemberRepository;
import com.mini.persistence.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memRepo;
	private final BoardRepository bRepo;
	private final ReviewRepository rRepo;
	private final CommentRepository cRepo;
	
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
	
	@Transactional
	public MemberRespDTO updateMember(MemberReqDTO reqDTO, String memberId) {
		
		Members targetMember = memRepo.findById(memberId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"멤버 없음"));
		
		if(targetMember.getProvider() == Provider.LOCAL) {
			//비번 체크 
			if(!encoder.matches(reqDTO.getPassword(), targetMember.getPassword()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 틀립니다.");
			
			targetMember.setPassword(encoder.encode(reqDTO.getNewPassword()));
		}
		
		if(!reqDTO.getNickname().equals(targetMember.getNickname()))
			targetMember.setNickname(reqDTO.getNickname());
		
		return MemberRespDTO.fromMemberEntity(targetMember);
	}
	
	@Transactional
	public void deleteMember(MemberDeleteDTO delDTO, String memberId) {
		Members targetMember = memRepo.findById(memberId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"멤버 없음"));
		
		if(targetMember.getProvider() == Provider.LOCAL) {
			//비번 체크 
			if(!encoder.matches(delDTO.getPassword(), targetMember.getPassword()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 틀립니다.");
		}
		else if(targetMember.getProvider() == Provider.GOOGLE) {
			
		}
		else if(targetMember.getProvider() == Provider.NAVER) {
			
		}
				
		memRepo.delete(targetMember);
	}
}
