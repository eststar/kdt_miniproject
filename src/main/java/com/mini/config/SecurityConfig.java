package com.mini.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.mini.config.filter.JWTAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	private final AuthenticationConfiguration authenticationConfig;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.csrf(csrf->csrf.disable());
		http.httpBasic(basic->basic.disable());
		http.formLogin(formLogin->formLogin.disable());
		http.authorizeHttpRequests(auth->auth.anyRequest().permitAll());
		http.oauth2Client(oauth2->oauth2.disable());
		http.oauth2Login(oauth2->oauth2.disable());
		http.sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilter(new JWTAuthenticationFilter(authenticationConfig.getAuthenticationManager()));
		
		return http.build();
	}
}
