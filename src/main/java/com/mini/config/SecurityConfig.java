package com.mini.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.mini.config.filter.JWTAuthenticationFilter;
import com.mini.config.filter.JWTAuthorizationFilter;
import com.mini.persistence.MemberRepository;

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
		http.cors(cors->cors.configurationSource(corsSource()));
		http.csrf(csrf->csrf.disable());
		http.httpBasic(basic->basic.disable());
		http.formLogin(formLogin->formLogin.disable());
		http.authorizeHttpRequests(auth->auth.requestMatchers("/login_page/**", "/login/**").permitAll()
							.anyRequest().permitAll());
		
		http.sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.addFilterBefore(new JWTAuthorizationFilter(memRepo), AuthorizationFilter.class);
		http.addFilter(new JWTAuthenticationFilter(authenticationConfig.getAuthenticationManager()));
		http.oauth2Login(oauth2->oauth2.successHandler(oauth2SuccessHandler));
		return http.build();
	}
	
	private CorsConfigurationSource corsSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOriginPatterns(Arrays.asList("http://localhost:3000", "http://127.0.0.1:3000", "http://10.125.121.182:3000", "https://10.125.121.182:3000"));
		config.addAllowedMethod(CorsConfiguration.ALL);
		config.addAllowedHeader(CorsConfiguration.ALL);
		config.setAllowCredentials(true);
		config.addExposedHeader(HttpHeaders.AUTHORIZATION);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
