package com.mini.config.filter;

import java.io.IOException;
import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini.domain.SecurityUser;
import com.mini.dto.MemberDTO;
import com.mini.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private final AuthenticationManager authenticationManager;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		ObjectMapper mapper = new ObjectMapper();
		MemberDTO member = null;
		try {
			member = mapper.readValue(request.getInputStream(), MemberDTO.class);
		} catch (IOException e) {
			return null;
		}
		
		if(member == null)
			return null;
		
		Authentication authToken = new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());
		return authenticationManager.authenticate(authToken); //SecurityUserDetailsService 의 loadUserByUsername
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		SecurityUser user = (SecurityUser)authResult.getPrincipal();
				
		Collection<? extends GrantedAuthority> authorites = user.getAuthorities();
		String role = "LOCAL";
		for(GrantedAuthority authority : authorites) {
			role = authority.toString();
			break;
		}
		
		//멤버 memberid(provider_username으로 만든 PK정보), provider, role 정보 넣어서 JWT 토큰 생성
		String token = JWTUtil.getJWT(user.getUsername(), user.getProvider().name(), role);//user 이름으로 토큰 생성
		
//		response.addHeader(HttpHeaders.AUTHORIZATION, token);
		Cookie jwtCookie = new Cookie("jwtToken", token.replace(JWTUtil.prefix, ""));
		jwtCookie.setHttpOnly(true);
		jwtCookie.setSecure(false);
		jwtCookie.setPath("/");
		jwtCookie.setMaxAge(60*60);
		response.addCookie(jwtCookie);
		
		response.setStatus(HttpStatus.OK.value());
		System.out.println("success:[JWTAuthFilter] " + user);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		System.out.println("unsuccessAuth" + failed);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
	}
	
	
}
	