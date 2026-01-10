package com.mini.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mini.domain.MemberErrorCode;
import com.mini.dto.ErrorRespDTO;
import com.mini.exception.MemberException;

@RestControllerAdvice
public class GlobalExceptionController {
	@ExceptionHandler(MemberException.class)
	public ResponseEntity<?> handleMemberException(MemberException e){
		MemberErrorCode errorCode = e.getErrorCode();
		return ResponseEntity.status(errorCode.getHttpStatus()).body(ErrorRespDTO.of(errorCode));
	}
}
