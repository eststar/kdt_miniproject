package com.mini.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.dto.BoardReqDTO;
import com.mini.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/test/board")
@RequiredArgsConstructor
public class BoardController {
	private final BoardService boardService;
	
	@GetMapping("/getboard")
	public ResponseEntity<?> getAllBoardWithMember(@ModelAttribute BoardReqDTO boardReq){
		return null;
	}
	
	@GetMapping("/getboardpage")
	public ResponseEntity<?> getAllBoardPageWithMember(@ModelAttribute BoardReqDTO boardReq){
		return null;
	}
	
	@PostMapping("/postboard")
	public ResponseEntity<?> postBoard(){
		return null;
	}
	
	@PatchMapping("/patchboard")
	public ResponseEntity<?> patchBoard(){
		return null;
	}
}
