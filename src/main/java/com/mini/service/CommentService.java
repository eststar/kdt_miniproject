package com.mini.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mini.domain.Board;
import com.mini.domain.Comment;
import com.mini.domain.Members;
import com.mini.dto.CommentReqDTO;
import com.mini.dto.CommentRespDTO;
import com.mini.dto.MemberDTO;
import com.mini.dto.PageRespDTO;
import com.mini.persistence.BoardRepository;
import com.mini.persistence.CommentRepository;
import com.mini.persistence.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository cRepo;
	private final MemberRepository memRepo;
	private final BoardRepository bRepo;

	public List<CommentRespDTO> getCommentAllWithMember(Long boardId) {
		List<Comment> cList = cRepo.getAllByBoardIdWithMember(boardId);
		List<CommentRespDTO> dtoList = new ArrayList<>();
		for (Comment c : cList) {
			dtoList.add(CommentRespDTO.fromCommentEntity(c));
		}
		return dtoList;
	}

	public PageRespDTO<CommentRespDTO> getCommentPageWithMember(Long boardId, CommentReqDTO reqDTO) {
		Page<Comment> cPage = cRepo.getCommentPageWithMember(boardId,
				PageRequest.of(reqDTO.getPageNum(), reqDTO.getPageSize()));
		List<CommentRespDTO> dtoList = new ArrayList<>();
		for (Comment c : cPage.getContent()) {
			dtoList.add(CommentRespDTO.fromCommentEntity(c));
		}
		return new PageRespDTO<>(cPage, dtoList);
	}

	public CommentRespDTO postComment(CommentReqDTO commentReq, MemberDTO memberDTO) {
		Members currentMember = memRepo.getReferenceById(memberDTO.getMemberId());
		Board currentBoard = bRepo.getReferenceById(commentReq.getBoardId());

		Comment targetC = cRepo.save(
				Comment.builder().content(commentReq.getContent()).member(currentMember).board(currentBoard).build());
		return CommentRespDTO.fromCommentEntity(targetC);
	}

	@Transactional
	public CommentRespDTO updateComment(CommentReqDTO commentReq, MemberDTO memberDTO, Boolean isAdmin) {
		Comment currentComment = cRepo.findById(commentReq.getCommentId())
				.orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없음"));

		if (!currentComment.getMember().getMemberId().equals(memberDTO.getMemberId()) && !isAdmin)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "수정 권한 없음");

		currentComment.changeContent(commentReq.getContent());
		return CommentRespDTO.fromCommentEntity(currentComment);
	}

	public Long deleteComment(Long commentId, MemberDTO memberDTO, Boolean isAdmin) {
		Comment targetComment = cRepo.findById(commentId)
				.orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없음"));
		Long targetId = targetComment.getCommentId();
		if (!targetComment.getMember().getMemberId().equals(memberDTO.getMemberId()) && !isAdmin)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "삭제 권한 없음");
		
		cRepo.delete(targetComment);
		return targetId;
	}
}
