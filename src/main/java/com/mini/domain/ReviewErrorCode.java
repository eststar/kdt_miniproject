package com.mini.domain;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode {
	REVIEW_BAD_REQUEST(HttpStatus.BAD_REQUEST, "R001", "잘못된 요청 - top 또는 bottom을 입력하세요");
	
	private final HttpStatus httpStatus;
	private final String errorCode;
	private final String message;
}
