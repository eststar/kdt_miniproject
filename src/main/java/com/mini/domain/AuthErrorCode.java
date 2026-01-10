package com.mini.domain;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements CustomErrorCode{
	LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "A001", "로그인 필요"),
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A002", "유효하지 않은 토큰"),
	EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "A003", "만료된 토큰"),
	ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "A004", "접근 권한 없음"),
	OAUTH2_LOGIN_FAILURE(HttpStatus.UNAUTHORIZED, "A005", "소셜 로그인 실패")
	;
	
	HttpStatus httpStatus;
	String errorCode;
	String message;
}
