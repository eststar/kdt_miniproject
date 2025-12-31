package com.mini.exception;

import com.mini.domain.MemberErrorCode;

public class UsernameDuplicateException extends MemberException{

	private static final long serialVersionUID = 1L;

	public UsernameDuplicateException() {
		super(MemberErrorCode.DUPLICATE_USERNAME);
	}

	
}
