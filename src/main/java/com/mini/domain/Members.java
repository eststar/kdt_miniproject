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
	private String 			memberId;    //2provider_username
	private OffsetDateTime 	createDate;  //3생성일
	@Builder.Default
	private Long 			tpCnt = 0L;  //4휴지배달개수
	@Column(columnDefinition = "TEXT")
	private String 			password;    //5비밀번호
	@Enumerated(EnumType.STRING)
	private Role			role;        //6권한
	@Column(columnDefinition = "TEXT")
	private String 	nickname;	 		//7닉네임
	@Enumerated(EnumType.STRING)
	private Provider 		provider;   //8로그인방식
	@Column(columnDefinition = "TEXT")
	private String 			username;	//9이메일 또는 유저id
	private Boolean 		enabled;	//10활성화여부
}
