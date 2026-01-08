package com.mini.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.dto.ReviewReqDTO;
import com.mini.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/test/review")
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService reviewService;
	
	@GetMapping("/getreview")
	public ResponseEntity<?> getAllWithToiletinfoAndMember(@RequestBody ReviewReqDTO reviewReq){
		
		return ResponseEntity.ok(reviewService.getAllWithMember(reviewReq.getDataCd()));
	}
	
	@GetMapping("/getreviewstat")
	public ResponseEntity<?> getAveragePointStat(@RequestBody ReviewReqDTO reviewReq){
		return ResponseEntity.ok(reviewService.getAveragePointStat(reviewReq));
	}
}
