package com.mini.exception;

import com.mini.domain.MemberErrorCode;

public class NicknameDuplicateException extends MemberException{
	private static final long serialVersionUID = 1L;

	public NicknameDuplicateException() {
		super(MemberErrorCode.DUPLICATE_NICKNAME);
	}
	
}
