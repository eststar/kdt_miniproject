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

public class EmergencyBoard {
	@Id
	private Long boardId;               // 게시글ID
	private OffsetDateTime createDate;  // 작성시간
	private String title;               // 제목
	private String content;             // 내용
	@ManyToOne                          
	@JoinColumn(name = "data_cd")       
	private String dataCd;				// 작성한 화장실정보(위치)
	@ManyToOne
	@JoinColumn(name = "member_id")
	private String memberId;			// 작성한 멤버정보
}
