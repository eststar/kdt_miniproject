package com.mini.dto;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mini.domain.Board;
import com.mini.domain.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDetailRespDTO {
	private Long boardId;               // 게시글ID
	private OffsetDateTime createDate;  // 작성시간
	private String title;               // 제목
	private String content;             // 내용
	private MemberRespDTO member;		// 작성한 멤버정보
	private List<CommentRespDTO> commentList; 
	
	public static BoardDetailRespDTO fromBoardEntity(Board board) {
		List<CommentRespDTO> dtoList = new ArrayList<>();
		for(Comment c : board.getCommentList()) {
			dtoList.add(CommentRespDTO.fromCommentEntity(c));
		}
		
		return BoardDetailRespDTO.builder()
					.boardId(board.getBoardId())
					.createDate(board.getCreateDate())
					.title(board.getTitle())
					.content(board.getContent())
					.member(MemberRespDTO.fromMemberEntity(board.getMember()))
					.commentList(dtoList)
					.build();
	}
}
