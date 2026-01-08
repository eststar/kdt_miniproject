package com.mini.dto;

import com.mini.domain.Members;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRespDTO {
	private String 	memberId;    //provider_username
	private String 	nickname;	 //이름
	
	public static MemberRespDTO fromMemberEntity(Members member) {
		return MemberRespDTO.builder()
				.memberId(member.getMemberId())
				.nickname(member.getNickname())
				.build();
	}
	
}