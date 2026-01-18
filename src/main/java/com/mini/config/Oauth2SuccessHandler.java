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
		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
		
		
		String origin = request.getHeader("Origin");
		if (origin == null)
			origin = request.getHeader("Referer");

		String dynamicBase = frontTestUrl;
		//프론트주소 판단 : 로컬 ip localhost의 경우
		if (origin != null && (origin.contains("localhost") || origin.contains("127.0.0.1")
				|| origin.matches(".*10\\.\\d+\\.\\d+\\.\\d+.*")))
			dynamicBase = origin.replaceAll("^(https?://[^/]+).*$", "$1");
		
		String savedTarget = (String) request.getSession().getAttribute("FINAL_TARGET");
		String targetURL = dynamicBase+"/main"; 

		if (savedTarget != null && !savedTarget.isEmpty()) {
	        System.out.println(">>> [성공] 세션에서 꺼낸 주소: " + savedTarget);
	        if (savedTarget.startsWith("/"))
	            targetURL = dynamicBase + savedTarget;
	        else
	            targetURL = savedTarget; // http... 로 시작하는 전체 주소면 그대로 사용
	        
	        request.getSession().removeAttribute("FINAL_TARGET");
	    } 
	    else
	    	targetURL = dynamicBase + "/main";
		
		if (targetURL.contains("?")) {
		    targetURL += "&loginSuccess=true";
		} else {
		    targetURL += "?loginSuccess=true";
		}
						
	    System.out.println("=== 리다이렉트 주소 ===:"+targetURL);
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
