package com.mini.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mini.domain.Members;
import com.mini.domain.Provider;
import com.mini.domain.SecurityUser;
import com.mini.dto.MemberDTO;
import com.mini.dto.MemberDeleteDTO;
import com.mini.dto.MemberReqDTO;
import com.mini.service.MemberService;
import com.mini.util.JWTUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memService;
	
	@GetMapping("/myinfo")
	public ResponseEntity<?> getMember(@AuthenticationPrincipal SecurityUser userInfo){
		if(userInfo == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		Members member = userInfo.getMember(); 
		MemberDTO responseDTO = MemberDTO.fromMemberEntity(member);		
		return ResponseEntity.ok(responseDTO);
	}
	
	@PostMapping("/signup") //로컬db 회원가입 username, password, nickname 만 전달됨
	public ResponseEntity<?> signUp(@RequestBody MemberDTO member){
		member.setProvider(Provider.LOCAL);
		
		MemberDTO success = memService.signUp(member);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(success);
	}
	
	@PatchMapping("/update")
	public ResponseEntity<?> updateMember(@RequestBody MemberReqDTO reqDTO, @AuthenticationPrincipal SecurityUser userInfo){
		return ResponseEntity.ok(memService.updateMember(reqDTO, userInfo.getMemberId()));
	}
	
	@DeleteMapping("/signout")
	public ResponseEntity<Void> deleteMember(@RequestBody MemberDeleteDTO delDTO, @AuthenticationPrincipal SecurityUser userInfo){
		memService.deleteMember(delDTO, userInfo.getMemberId());
		ResponseCookie deleteCookie = JWTUtil.makeJWTTokenCookie("", 0);
		return ResponseEntity.noContent().header(HttpHeaders.SET_COOKIE, deleteCookie.toString()).build();
	}
}
