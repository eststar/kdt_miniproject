package com.mini.config;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Oauth2FailureHandler extends SimpleUrlAuthenticationFailureHandler{@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String targetURL = request.getHeader("Referer");
		String defaultURL = "https://10.125.121.182:3000/login";
		if(targetURL == null || targetURL.isEmpty())
			targetURL = defaultURL;
		
		System.out.println("Oauth2 로그인 실패: " + exception.getMessage());
		getRedirectStrategy().sendRedirect(request, response, targetURL);
	}
	
}
