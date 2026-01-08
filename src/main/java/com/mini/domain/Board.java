package com.mini.domain;

import java.time.OffsetDateTime;

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

public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardId;               // 게시글ID
	@Builder.Default
	@Column(columnDefinition = "TIMESTAMPTZ DEFAULT NOW()")
	private OffsetDateTime createDate = OffsetDateTime.now();  // 작성시간
	@Column(columnDefinition = "TEXT")
	private String title;               // 제목
	@Column(columnDefinition = "TEXT")
	private String content;             // 내용
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Members member;			// 작성한 멤버정보
}
