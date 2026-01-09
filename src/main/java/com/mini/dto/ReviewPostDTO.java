package com.mini.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewPostDTO {
	private String 	dataCd;		//리뷰 작성 화장실 정보
	private String 	content; 	//리뷰내용    
	private Integer point;		//별점
	
}
