package com.mini.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController("/api/test/emergencyboard")
@RequiredArgsConstructor
public class EmergencyBoardController {
	@GetMapping("/getboard")
	public ResponseEntity<?> getBoard(){
		return null;
	}
}
