package com.mini.domain;

import java.util.Date;

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

public class Member {
	@Id
	@Column(name = "member_id")
	private String 	memberId;    //아이디
	private Date 	createDate;  //생성일
	private Long 	tpCnt;       //휴지배달개수
	private String 	password;    //비밀번호
	private Role	role;        //권한
	private String 	nickname;	 //이름
	private Provider provider;   //로그인방식
}
