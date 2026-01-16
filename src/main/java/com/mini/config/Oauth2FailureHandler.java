package com.mini.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Oauth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {
	@Value("${app.frontend.url}")
	private String frontTestURL;

	@Value("${frontvercel.url}")
	private String frontURL;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		String origin = request.getHeader("Origin");
        if (origin == null) origin = request.getHeader("Referer");

        String dynamicBase = frontTestURL; // 기본값
        if (origin != null && (origin.contains("localhost") || origin.contains("127.0.0.1") || origin.matches(".*10\\.\\d+\\.\\d+\\.\\d+.*")))
            dynamicBase = origin.replaceAll("^(https?://[^/]+).*$", "$1");
        
        // 2. 실패 시 돌아갈 페이지 설정 (보통 로그인 페이지)
        String targetURL = dynamicBase + "/login?error";

        System.out.println(">>> Oauth2 로그인 실패: " + exception.getMessage());
        System.out.println(">>> 실패 리다이렉트 주소: " + targetURL);

		getRedirectStrategy().sendRedirect(request, response, targetURL);
	}

}
