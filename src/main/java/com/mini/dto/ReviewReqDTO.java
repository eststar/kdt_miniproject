package com.mini.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewReqDTO {
	private Long 	reviewId; 	// 리뷰ID
	
	private String 	memberId; 	//리뷰 작성한 멤버정보
	private String 	dataCd;		//리뷰 작성 화장실 정보
	private String 	topBottom;	//최고최하
	
	private String 	content; 	//리뷰내용    
	private Integer point;		//별점
	
	private Integer pageNum = 0;
	private Integer pageSize = 10;
}
