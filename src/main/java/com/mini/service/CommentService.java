package com.mini.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mini.domain.CommentReqDTO;
import com.mini.domain.CommentRespDTO;
import com.mini.dto.MemberDTO;
import com.mini.persistence.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository cRepo;
	
	public List<CommentRespDTO> getAllWithMember(CommentReqDTO commentReq){
		return null;
	}
	
	public CommentRespDTO postComment(CommentReqDTO commentReq, MemberDTO memberDTO) {
		return null;
	}
	
	public CommentRespDTO updateComment(CommentReqDTO commentReq, MemberDTO memberDTO, Boolean isAdmin) {
		return null;
	}
	
	public Long deleteComment(Long commentId, MemberDTO memberDTO, Boolean isAdmin) {
		return null;
	}
}
