package com.mini.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class Reviews {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long 			reviewId; 	// 리뷰ID
	@Builder.Default
	@Column(columnDefinition = "DATE DEFAULT CURRENT_DATE")
	private LocalDate 	createDate = LocalDate.now(); //작성시간
	@Column(columnDefinition = "TEXT")
	private String 			content; 	//리뷰내용               
	@ManyToOne(fetch = FetchType.LAZY)                            
	@JoinColumn(name = "data_cd", nullable = false)
	private ToiletInfo		toiletinfo; 	//리뷰작성한 화장실 정보
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Members 		member; 	//리뷰 작성한 멤버정보
	private Integer 		point; 		//평점
}
