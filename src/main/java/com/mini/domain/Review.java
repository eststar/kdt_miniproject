package com.mini.domain;

import java.time.OffsetDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Review {
	@Id                                   
	private String reviewId; // 리뷰ID             
	private OffsetDateTime createDate; //작성시간
	private Long viewCnt; //   조회수                
	private String content; //리뷰내용               
	@ManyToOne                            
	@JoinColumn(name = "data_cd")
	private String dataCd; //리뷰작성한 화장실 정보
	@ManyToOne
	@JoinColumn(name = "member_id")
	private String memberId; //리뷰 작성한 멤버정보
	private Integer point; //평점
}
