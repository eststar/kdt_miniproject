package com.mini.dto;

import java.time.LocalDate;

import com.mini.domain.Reviews;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO {
	private Long 			reviewId; 	// 리뷰ID
	private LocalDate 		createDate; //작성시간
	private String 			content; 	//리뷰내용               
	private MemberRespDTO 	member; 	//리뷰 작성한 멤버정보
	private Integer 		point; 		//별점
	
	private String 			dataCd;		//화장실 정보
	public static ReviewDTO fromReviewEntity(Reviews review) {
		return ReviewDTO.builder()
						.reviewId(review.getReviewId())
						.createDate(review.getCreateDate())
						.content(review.getContent())
						.point(review.getPoint())
						.member(MemberRespDTO.builder()
											.memberId(review.getMember().getMemberId())
											.nickname(review.getMember().getNickname())
											.build())
						.build();
	}
}
