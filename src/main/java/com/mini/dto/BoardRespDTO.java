package com.mini.dto;

import java.time.OffsetDateTime;

import com.mini.domain.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//전체 board조회용 dto

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardRespDTO {
	private Long boardId;               // 게시글ID
	private OffsetDateTime createDate;  // 작성시간
	private String title;               // 제목
	private String content;             // 내용
	private MemberRespDTO member;		// 작성한 멤버정보
	@Builder.Default
	private Long commentCnt = 0L; 
	
	public static BoardRespDTO fromBoardEntity(Board board, Long commentCnt) {
		
		return BoardRespDTO.builder()
					.boardId(board.getBoardId())
					.createDate(board.getCreateDate())
					.title(board.getTitle())
					.content(board.getContent())
					.member(MemberRespDTO.fromMemberEntity(board.getMember()))
					.commentCnt(commentCnt)
					.build();
	}
}
