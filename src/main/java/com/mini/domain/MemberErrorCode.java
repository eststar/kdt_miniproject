package com.mini.domain;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements CustomErrorCode{
	MEMBER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "M001", "존재하지 않는 멤버입니다"),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "M002", "비밀번호가 일치하지 않습니다."),
	DUPLICATE_USERNAME(HttpStatus.CONFLICT, "M003", "이미 사용중인 유저이름"),
	DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "M004", "이미 사용중인 닉네임")
	;
	
	private final HttpStatus httpStatus;
	private final String errorCode;
	private final String message;
}
