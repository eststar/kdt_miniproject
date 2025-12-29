package com.mini.domain;


import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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

public class Members {
	@Id
	@Column(name = "member_id", columnDefinition = "TEXT")
	private String 			memberId;    //provider_유저이메일
	private OffsetDateTime 	createDate;  //생성일
	@Builder.Default
	private Long 			tpCnt = 0L;       //휴지배달개수
	@Column(columnDefinition = "TEXT")
	private String 			username;		//이메일 또는 유저id
	@Column(columnDefinition = "TEXT")
	private String 			password;    //비밀번호
	@Enumerated(EnumType.STRING)
	private Role			role;        //권한
	@Column(columnDefinition = "TEXT")
	private String 	nickname;	 //이름
	@Enumerated(EnumType.STRING)
	private Provider 		provider;   //로그인방식
	private Boolean 		enabled;
}
