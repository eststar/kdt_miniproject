package com.mini.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewReqDTO {
	private Long 	reviewId; 	// 리뷰ID
	
	private String 	memberId; 	//리뷰 작성한 멤버정보
	private String 	dataCd;
	private String 	topBottom;
	
	private Integer pageNum = 0;
	private Integer pageSize = 10;
}
