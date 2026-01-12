package com.mini.domain;

import com.mini.dto.MemberRespDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CommentRespDTO {
	private Long 			commentId; 	// 댓글ID
	private String 			content; 	//댓글내용               
	private MemberRespDTO 	member; 	//댓글 작성한 멤버정보
	private Long 			boardId;
	
	public static CommentRespDTO fromCommentEntity(Comment comment) {
		return CommentRespDTO.builder()
							.commentId(comment.getCommentId())
							.content(comment.getContent())
							.member(MemberRespDTO.fromMemberEntity(comment.getMember()))
							.boardId(comment.getBoard().getBoardId())
							.build();
	}
}
