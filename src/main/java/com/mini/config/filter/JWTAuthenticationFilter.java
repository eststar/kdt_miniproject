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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini.domain.Members;
import com.mini.domain.ToiletUserDetails;
import com.mini.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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
		Members member = null;
		try {
			member = mapper.readValue(request.getInputStream(), Members.class);
		} catch (IOException e) {
			return null;
		}
		
		if(member == null)
			return null;
		
		Authentication authToken = new UsernamePasswordAuthenticationToken(member.getMemberId(), member.getPassword());
		return authenticationManager.authenticate(authToken); //SecurityUserDetailsService 의 loadUserByUsername
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		ToiletUserDetails user = (ToiletUserDetails)authResult.getPrincipal();
		
		System.out.println("[JWTAuthFilter] " + user);
		
		Collection<? extends GrantedAuthority> authorites = user.getAuthorities();
		String role = "";
		for(GrantedAuthority authority : authorites) {
			role = authority.toString();
			break;
		}
		
		//멤버 emailID, provider, role 정보 넣어서 JWT 토큰 생성
		String token = JWTUtil.getJWT(user.getUsername(), user.getProvider().name(), role);//user 이름으로 토큰 생성
		
		response.addHeader(HttpHeaders.AUTHORIZATION, token);
		response.setStatus(HttpStatus.OK.value());
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		System.out.println("unsuccessAuth" + failed);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
	}
	
	
}
	