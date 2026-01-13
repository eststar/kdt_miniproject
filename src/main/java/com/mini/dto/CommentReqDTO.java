package com.mini.dto;

import com.mini.domain.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CommentReqDTO {
	private Long 		commentId; 	// 댓글ID
	private String 		content; 	//댓글내용               
	private String 		memberId; 	//댓글 작성한 멤버정보
	private Long 		boardId;
	
	public static CommentReqDTO fromCommentEntity(Comment comment) {
		return CommentReqDTO.builder()
							.commentId(comment.getCommentId())
							.content(comment.getContent())
							.memberId(comment.getMember().getMemberId())
							.boardId(comment.getBoard().getBoardId())
							.build();
	}
}
