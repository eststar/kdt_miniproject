package com.mini.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.domain.SecurityUser;
import com.mini.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController("/api/memberinfo")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memService;
	
//	@GetMapping("/myinfo")
//	public ResponseEntity<?> getMember(@AuthenticationPrincipal SecurityUser security){
//		if()
//		return ResponseEntity.ok(response);
//	}
}
