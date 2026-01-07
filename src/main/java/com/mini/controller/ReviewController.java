package com.mini.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController("/api/test/review")
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService reviewService;
	
	@GetMapping("/getreviews")
	public ResponseEntity<?> getAllWithToiletinfoAndMember(String toiletinfo){
		
		return ResponseEntity.ok(reviewService.getAllWithToiletinfoAndMember(toiletinfo));
	}
}
