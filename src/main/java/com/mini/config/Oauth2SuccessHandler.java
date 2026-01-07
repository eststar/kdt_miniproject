package com.mini.config;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.mini.dto.MemberDTO;
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
	
	@Value("${app.frontend.url}")
	private String successUrl;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		Map<String, String> map = getUserInfo(authentication);
		String username = map.get("email");
		String memberId = map.get("provider").toUpperCase() + "_" + username;
		String nickname = map.get("nickname");
		String oauth2pass = "OAUTH2_USER";
		MemberDTO memdto = memService.save(memberId, username, map.get("provider").toUpperCase(), oauth2pass, nickname);
		
		String token = JWTUtil.getJWT(memdto.getMemberId());
		Cookie cookie = JWTUtil.makeJWTTokenCookie(token, 60*30);
		response.addCookie(cookie);
//		response.sendRedirect(frontUrl);
		getRedirectStrategy().sendRedirect(request, response, successUrl);
	}
	
//	@SuppressWarnings("unchecked")
	private Map<String, String> getUserInfo(Authentication auth){
		OAuth2AuthenticationToken oAuth2Token = (OAuth2AuthenticationToken)auth; 
		String provider = oAuth2Token.getAuthorizedClientRegistrationId();
		OAuth2User oAuth2User = (OAuth2User)oAuth2Token.getPrincipal();
		
		String email = "unknown";
		String nickname = oAuth2User.getName();
		if(provider.equalsIgnoreCase("google")) {
			email = (String)oAuth2User.getAttribute("email");
			nickname = (String)oAuth2User.getAttribute("name");
		}	
		else if(provider.equalsIgnoreCase("naver")) {
			Map<String, String> respMap = oAuth2User.getAttribute("response");
			email = (String)(respMap.get("email"));
			nickname = (String)(respMap.get("nickname"));
		}
				
		return Map.of("provider", provider, "email", email, "nickname", nickname);
	}
	
}
