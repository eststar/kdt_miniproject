package com.mini.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AveragePointDTO {
	private String 	toiletNm; 	// 화장실명
	private Double point; 		// 평균별점	
	private Long	reviewCnt; 	//리뷰개수
}
