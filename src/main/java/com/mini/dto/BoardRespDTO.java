package com.mini.dto;

import java.time.OffsetDateTime;

import com.mini.domain.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardRespDTO {
	private Long boardId;               // 게시글ID
	private OffsetDateTime createDate;  // 작성시간
	private String title;               // 제목
	private String content;             // 내용
	private String memberId;			// 작성한 멤버정보
	private String nickname;
	
	public static BoardRespDTO fromBoardEntity(Board board) {
		return BoardRespDTO.builder()
					.boardId(board.getBoardId())
					.createDate(board.getCreateDate())
					.title(board.getTitle())
					.content(board.getContent())
					.memberId(board.getMember().getMemberId())
					.build();
	}
}
