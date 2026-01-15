package com.mini.config;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.mini.dto.MemberDTO;
import com.mini.service.MemberService;
import com.mini.util.JWTUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	
	private final MemberService memService;
	
	@Value("${app.frontend.url}")
	private String frontTestUrl;
	
	@Value("${frontvercel.url}")
	private String frontURL;
	
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
		ResponseCookie cookie = JWTUtil.makeJWTTokenCookie(token, 60*60*12);
//		System.out.println("생성된 쿠키 문자열: " + cookie.toString());
//		response.addHeader("Set-Cookie", cookie.toString());
		response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
//		response.sendRedirect(frontUrl);
		
		String targetURL = request.getHeader("Referer"); // 요청 온 곳 확인
	    
	    // 만약 Referer가 없거나, 구글 주소거나, 백엔드 주소라면 프론트 메인으로 보냄
	    if (targetURL == null || targetURL.contains("google.com") || targetURL.contains("ngrok")) {
	        targetURL = frontURL; 
	    }
		getRedirectStrategy().sendRedirect(request, response, targetURL);
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
