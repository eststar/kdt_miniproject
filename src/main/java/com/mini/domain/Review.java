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
	private String reviewId;
	private OffsetDateTime createDate;
	private Long viewCnt;
	private String content;
	@ManyToOne
	@JoinColumn(name = "data_cd")
	private String dataCd;
	@ManyToOne
	@JoinColumn(name = "member_id")
	private String memberId;
	private Integer point;
}
