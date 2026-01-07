package com.mini.dto;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mini.domain.Members;
import com.mini.domain.Provider;
import com.mini.domain.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberReviewRespDTO {
	private String username;//이메일 또는 단어형식 id
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	private String 			memberId;    //provider_username
	@Builder.Default
	private Long 			tpCnt = 0L;       //휴지배달개수
	private String 	nickname;	 //이름
	private Boolean 		enabled; //활성화 여부
	
	public static MemberDTO fromMemberEntity(Members member) {
		return MemberDTO.builder()
				.memberId(member.getMemberId())
				.nickname(member.getNickname())
				.username(member.getUsername())
				.tpCnt(member.getTpCnt())
				.enabled(member.getEnabled())
				.build();
	}
	
}