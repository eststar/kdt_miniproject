package com.mini.dto;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mini.domain.Members;
import com.mini.domain.Provider;
import com.mini.domain.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDTO {
	private String username;//이메일 또는 단어형식 id
	@JsonIgnore
	private String password;
	
	private String 			memberId;    //provider_username
	private OffsetDateTime 	createDate;  //생성일
	@Builder.Default
	private Long 			tpCnt = 0L;       //휴지배달개수
	private Role			role;        //권한
	private String 	nickname;	 //이름
	private Provider 		provider;   //로그인방식
	private Boolean 		enabled; //활성화 여부
	
	public static MemberDTO fromMemberEntity(Members member) {
		return MemberDTO.builder()
				.nickname(member.getNickname())
				.username(member.getUsername())
				.createDate(member.getCreateDate())
				.tpCnt(member.getTpCnt())
				.role(member.getRole())
				.provider(member.getProvider())
				.enabled(member.getEnabled())
				.build();
	}
}
