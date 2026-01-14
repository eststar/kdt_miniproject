package com.mini.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.domain.SecurityUser;
import com.mini.dto.BoardReqDTO;
import com.mini.dto.MemberDTO;
import com.mini.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/test/board")
@RequiredArgsConstructor
public class BoardController {
	private final BoardService boardService;
	
	@GetMapping("/getallboard")
	public ResponseEntity<?> getAllBoardWithMember(){
		return ResponseEntity.ok(boardService.getAllBoardWithMemberAndComment());
	}
	
	//pageNum pageSize 받아야함
	@GetMapping("/getpageboard")
	public ResponseEntity<?> getBoardPageWithMember(@ModelAttribute BoardReqDTO boardReq){
		return ResponseEntity.ok(boardService.getBoardPageWithMemberAndComment(boardReq));
	}
	
	@GetMapping("/getboard/{boardId}")
	public ResponseEntity<?> getBoardDetailWithMember(@PathVariable Long boardId){
		return ResponseEntity.ok(boardService.getBoardDetailWithMember(boardId));
	}
	
	@PostMapping("/postboard")
	public ResponseEntity<?> postBoard(@RequestBody BoardReqDTO boardReq, @AuthenticationPrincipal SecurityUser userInfo){
		return ResponseEntity.ok(boardService.postBoard(boardReq, MemberDTO.builder().memberId(userInfo.getMemberId()).build()));
	}
	
	@PutMapping("/putboard")
	public ResponseEntity<?> patchBoard(@RequestBody BoardReqDTO boardUpdate, @AuthenticationPrincipal SecurityUser userInfo){
		return ResponseEntity.status(HttpStatus.OK).body(boardService.updateBoard(boardUpdate, MemberDTO.builder().memberId(userInfo.getMemberId()).build(), userInfo.isAdmin()));
	}
	
	@DeleteMapping("/deleteboard/{boardId}")
	public ResponseEntity<?> deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal SecurityUser userInfo){
		return ResponseEntity.status(HttpStatus.OK).body(Map.of("boardId", boardService.deleteBoard(boardId, MemberDTO.builder().memberId(userInfo.getMemberId()).build(), userInfo.isAdmin())));
	}
}
