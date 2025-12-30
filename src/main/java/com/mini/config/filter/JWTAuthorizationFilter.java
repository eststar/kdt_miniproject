package com.mini.config.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mini.domain.Members;
import com.mini.domain.SecurityUser;
import com.mini.persistence.MemberRepository;
import com.mini.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter{
	private final MemberRepository memRepo;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//쿠키에서 JWT얻어오기
		Cookie[] cookies = request.getCookies();
		String jwtToken = null;
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("jwtToken"))
					jwtToken = cookie.getValue();
			}
		}
		else{
			filterChain.doFilter(request, response);
			return;
		}
		
		//쿠키에 prefix 이미 제거한거라 제거 안된거 들어오면 return
		if(jwtToken == null || jwtToken.startsWith(JWTUtil.prefix)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String memberId = JWTUtil.getClaim(jwtToken, JWTUtil.memberidClaim);
		Optional<Members> opt = memRepo.findById(memberId);
		
		if(!opt.isPresent()) {
			filterChain.doFilter(request, response);
			return;
		}
		
		Members findMember = opt.orElseThrow(()->{
			log.error("토큰은 유효하나, DB에 해당하는 유저 없음:", memberId);
			return new UsernameNotFoundException("User not Found");									
		}); 
		
		SecurityUser user = new SecurityUser(findMember);
		
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		filterChain.doFilter(request, response);
	}
	
}
