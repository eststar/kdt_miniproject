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
	private String 	memberId;
	private Date 	createDate;
	private Long 	tpCnt;
	private String 	password;
	private Role	role;
	private String 	username;	
}
