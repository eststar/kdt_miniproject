package com.mini.exception;

import com.mini.domain.MemberErrorCode;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private final MemberErrorCode errorCode; 
	public MemberException(MemberErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
	
}
