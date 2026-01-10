package com.mini.dto;

import java.time.LocalDateTime;

import com.mini.domain.CustomErrorCode;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorRespDTO {
	private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;       // HTTP 상태 코드 (예: 409)
    private final String error;    // 에러 유형 (예: "Conflict")
    private final String code;     // 우리가 정의한 에러 코드 (예: "M001")
    private final String message;  // 상세 메시지
    
    public static ErrorRespDTO of(CustomErrorCode errorCode) {
        return ErrorRespDTO.builder()
                .status(errorCode.getHttpStatus().value())
                .error(errorCode.getHttpStatus().name()) 
                .code(errorCode.getErrorCode())
                .message(errorCode.getMessage())
                .build();
    }
}
