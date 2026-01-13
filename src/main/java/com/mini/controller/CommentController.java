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
import com.mini.dto.CommentReqDTO;
import com.mini.dto.MemberDTO;
import com.mini.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/test/comment")
@RequiredArgsConstructor
public class CommentController {
	private final CommentService cService;
	
//	@GetMapping("/getcomment")
//	public ResponseEntity<?> getAllCommentWithMember(@ModelAttribute CommentReqDTO commentReq){
//		return ResponseEntity.ok(cService.getAllWithMember(commentReq));
//	}
	
//	@GetMapping("/getboard/{boardId}")
//	public ResponseEntity<?> getCommentDetailWithMember(@PathVariable Long commentId){
//		return ResponseEntity.ok(cService);
//	}
	
	@PostMapping("/postcomment")
	public ResponseEntity<?> postComment(@RequestBody CommentReqDTO commentReq, @AuthenticationPrincipal SecurityUser userInfo){
		return ResponseEntity.ok(cService.postComment(commentReq, MemberDTO.builder().memberId(userInfo.getMemberId()).build()));
	}
	
	@PutMapping("/putcomment")
	public ResponseEntity<?> patchComment(@RequestBody CommentReqDTO commentReq, @AuthenticationPrincipal SecurityUser userInfo){
		return ResponseEntity.status(HttpStatus.OK).body(cService.updateComment(commentReq, MemberDTO.builder().memberId(userInfo.getMemberId()).build(), userInfo.isAdmin()));
	}
	
	@DeleteMapping("/deletecomment/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal SecurityUser userInfo){
		return ResponseEntity.status(HttpStatus.OK).body(Map.of("commentId", cService.deleteComment(commentId, MemberDTO.builder().memberId(userInfo.getMemberId()).build(), userInfo.isAdmin())));
	}
}
