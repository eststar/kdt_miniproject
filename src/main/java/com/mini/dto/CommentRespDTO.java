package com.mini.dto;

import java.time.OffsetDateTime;

import com.mini.domain.Comment;

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
	private OffsetDateTime 	createTime;
	
	public static CommentRespDTO fromCommentEntity(Comment comment) {
		return CommentRespDTO.builder()
							.commentId(comment.getCommentId())
							.content(comment.getContent())
							.createTime(comment.getCreateTime())
							.member(MemberRespDTO.fromMemberEntity(comment.getMember()))
							.boardId(comment.getBoard().getBoardId())
							.build();
	}
}
