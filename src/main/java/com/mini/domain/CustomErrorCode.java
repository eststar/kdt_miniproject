package com.mini.domain;

import org.springframework.http.HttpStatus;

public interface CustomErrorCode {
	HttpStatus getHttpStatus();
	String getErrorCode();     
	String getMessage();       
}
