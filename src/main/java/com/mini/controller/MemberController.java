package com.mini.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.domain.Members;
import com.mini.domain.SecurityUser;
import com.mini.dto.MemberDTO;
import com.mini.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
//	private final MemberService memService;
	
	@GetMapping("/myinfo")
	public ResponseEntity<?> getMember(@AuthenticationPrincipal SecurityUser secureUser){
		if(secureUser == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		Members member = secureUser.getMember(); 
		MemberDTO responseDTO = MemberDTO.fromMemberEntity(member);		
		return ResponseEntity.ok(responseDTO);
	}
}
