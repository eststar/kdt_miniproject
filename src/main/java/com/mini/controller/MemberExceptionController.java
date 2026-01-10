package com.mini.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mini.dto.ErrorRespDTO;
import com.mini.exception.UsernameDuplicateException;

@RestControllerAdvice(basePackages = "com.mini.controller.MemberController")
public class MemberExceptionController {
	@ExceptionHandler(UsernameDuplicateException.class)
	public ResponseEntity<ErrorRespDTO> handleUsernameDuplicateException(UsernameDuplicateException e){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorRespDTO.of(e.getErrorCode()));
	}
	
}
