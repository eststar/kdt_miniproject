package com.mini.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.domain.SecurityUser;
import com.mini.dto.MemberDTO;
import com.mini.dto.ReviewPostDTO;
import com.mini.dto.ReviewReqDTO;
import com.mini.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/test/review")
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService reviewService;
	
	@GetMapping("/getreview")
	public ResponseEntity<?> getAllWithToiletinfoAndMember(@ModelAttribute ReviewReqDTO reviewReq, @AuthenticationPrincipal SecurityUser userInfo){
		return ResponseEntity.ok(reviewService.getAllWithMember(reviewReq, userInfo));
	}
	
	@GetMapping("/getreviewstat")
	public ResponseEntity<?> getAveragePointStat(@ModelAttribute ReviewReqDTO reviewReq){
		return ResponseEntity.ok(reviewService.getAveragePointStat(reviewReq));
	}
	
	@PostMapping("/postreview")
	public ResponseEntity<?> postReview(@RequestBody ReviewPostDTO reviewPost, @AuthenticationPrincipal SecurityUser userInfo){
		return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.postReview(reviewPost, MemberDTO.builder().memberId(userInfo.getMemberId()).build()));
	}
	
	@PutMapping("/putreview")
	public ResponseEntity<?> putReview(@RequestBody ReviewPostDTO reviewUpdate, @AuthenticationPrincipal SecurityUser userInfo){
		return ResponseEntity.status(HttpStatus.OK).body(reviewService.updateReview(reviewUpdate, MemberDTO.builder().memberId(userInfo.getMemberId()).build(), userInfo.isAdmin()));
	}
	
	@DeleteMapping("/deletereview")
	public ResponseEntity<?> deleteReview(@RequestBody ReviewPostDTO reviewDelete, @AuthenticationPrincipal SecurityUser userInfo){
		return ResponseEntity.status(HttpStatus.OK).body(reviewService.deleteReview(reviewDelete, MemberDTO.builder().memberId(userInfo.getMemberId()).build(), userInfo.isAdmin()));
	}
}
