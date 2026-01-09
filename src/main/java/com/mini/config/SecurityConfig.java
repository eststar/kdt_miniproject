package com.mini.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.mini.config.filter.JWTAuthenticationFilter;
import com.mini.config.filter.JWTAuthorizationFilter;
import com.mini.domain.Role;
import com.mini.persistence.MemberRepository;
import com.mini.util.JWTUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
		
	private final AuthenticationConfiguration authenticationConfig;
	private final AuthenticationSuccessHandler oauth2SuccessHandler;
	private final MemberRepository memRepo;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.cors(cors->cors.configurationSource(corsSource())); //CORS 설정
		http.csrf(csrf->csrf.disable()); //csrf설정
		http.httpBasic(basic->basic.disable()); //authentication header 저장 ID:PW 사용하지 않는 방식으로
		http.formLogin(formLogin->formLogin.disable()); //UsernamePasswordAuthenticationFilter 사용안함-JWTAuthenticationFilter가 대체
		http.authorizeHttpRequests(auth->auth.requestMatchers(HttpMethod.POST, "/api/test/review/postreview").hasAnyRole("MEMBER", "ADMIN")
										.requestMatchers("/login_page/**", "/login/**", "/logout/**").permitAll()
							.anyRequest().permitAll()); //현재 임시로 전체 가능하게
		
		http.sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.addFilterBefore(new JWTAuthorizationFilter(memRepo), AuthorizationFilter.class); //인가처리 필터
		http.addFilter(new JWTAuthenticationFilter(authenticationConfig.getAuthenticationManager())); //인증처리 필터
		http.oauth2Login(oauth2->oauth2.successHandler(oauth2SuccessHandler));
		http.logout(logout->logout.logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler())); //로그아웃 처리
		return http.build();
	}
	
	//CORS 설정 
	private CorsConfigurationSource corsSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOriginPatterns(Arrays.asList("http://localhost:3000","https://localhost:3000" , "http://127.0.0.1:3000", "https://127.0.0.1:3000", "http://10.125.121.182:3000", "https://10.125.121.182:3000", "https://webfront-ashen.vercel.app"));
		config.addAllowedMethod(CorsConfiguration.ALL);
		config.addAllowedHeader(CorsConfiguration.ALL);
		config.setAllowCredentials(true);
		config.addExposedHeader(HttpHeaders.AUTHORIZATION);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
	
	//로그아웃시 처리
	private LogoutSuccessHandler logoutSuccessHandler() {
		return (request, response, authentication)->{
			Cookie cookie = JWTUtil.makeJWTTokenCookie(null, 0); //cookie 유효시간 0으로
					
//					new Cookie("jwtToken", null);
//			cookie.setHttpOnly(true); //
//			cookie.setSecure(false);
//			cookie.setPath("/");
//			cookie.setMaxAge(0); //cookie 유효시간 0으로
			response.addCookie(cookie);
			
			response.setStatus(HttpServletResponse.SC_OK);
			System.out.println("=== 로그아웃 처리 완료 (200 OK) ===");
		};
	}
}
