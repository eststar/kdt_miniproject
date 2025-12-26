package com.mini.domain;


import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	private String 	memberId;    //아이디
	private OffsetDateTime 	createDate;  //생성일
	private Long 	tpCnt;       //휴지배달개수
	@Column(columnDefinition = "TEXT")
	private String 	password;    //비밀번호
	private Role	role;        //권한
	@Column(columnDefinition = "TEXT")
	private String 	nickname;	 //이름
	private Provider provider;   //로그인방식
}
