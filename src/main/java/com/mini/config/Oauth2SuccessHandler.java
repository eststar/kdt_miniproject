package com.mini.config;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.mini.service.MemberService;
import com.mini.util.JWTUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	
	private final MemberService memService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		Map<String, String> map = getUserInfo(authentication);
		String username = map.get("email");
		String memberId = map.get("provider") + "_" + username;		
		String oauth2pass = "OAUTH2_USER";
		memService.save(memberId, username, map.get("provider"), oauth2pass);
		
		String token = JWTUtil.getJWT(memberId);
		response.addHeader(HttpHeaders.AUTHORIZATION, token);
		
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, String> getUserInfo(Authentication auth){
		OAuth2AuthenticationToken oAuth2Token = (OAuth2AuthenticationToken)auth; 
		String provider = oAuth2Token.getAuthorizedClientRegistrationId();
		OAuth2User oAuth2User = (OAuth2User)oAuth2Token.getPrincipal();
		
		String email = "unknown"; 
		if(provider.equalsIgnoreCase("google"))
			email = (String)oAuth2User.getAttributes().get("email");
		else if(provider.equalsIgnoreCase("naver"))
			email = (String)((Map<String, String>)oAuth2User.getAttribute("response")).get("email");
		
		return Map.of("provider", provider, "email", email);
	}
	
	void sendJWTtoClient(HttpServletResponse response, String token) throws IOException{
		Cookie cookie = new Cookie("jwtToken", token.replace(JWTUtil.prefix, ""));
		cookie.setHttpOnly(true);
		cookie.setSecure(false);
		cookie.setPath("/");
		cookie.setMaxAge(60*60);
		response.addCookie(cookie);
		
//		response.sendRedirect("https://webfront-ashen.vercel.app/main");
	}
}
