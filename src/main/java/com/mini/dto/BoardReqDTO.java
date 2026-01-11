package com.mini.dto;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardReqDTO {
	private Long boardId;               // 게시글ID
	private OffsetDateTime createDate;  // 작성시간
	private String title;               // 제목
	private String content;             // 내용
	private String memberId;			// 작성한 멤버정보
	private String nickname;
	
	private Integer pageNum = 0;
	private Integer pageSize = 10;
}
