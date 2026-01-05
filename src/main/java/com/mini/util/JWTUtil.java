package com.mini.util;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;

import jakarta.servlet.http.Cookie;

public class JWTUtil {
	private static final Long ACCESS_TOKEN_MSEC = 6L*60*(60*1000);
	private static final String JWT_KEY = "toiletMiniProject";
	
	public static final String prefix ="Bearer ";
	public static final String memberidClaim = "memberId";
	public static final String providerClaim = "provider";
	public static final String roleClaim = "role";
	
	private static String getJWTSource(String token) {
		if(token.startsWith(prefix))
			return token.replace(prefix, "");
		return token;
	}
	
	//JWT를 만들 때 호출 with DB
		public static String getJWT(String memberId) {
			String src = JWT.create().withClaim(memberidClaim, memberId)
									.withExpiresAt(new Date(System.currentTimeMillis()+ACCESS_TOKEN_MSEC))
									.sign(Algorithm.HMAC256(JWT_KEY));
			return prefix + src;
		}
	
	public static String getJWT(String memberId, String provider, String role) {
		String src = JWT.create().withClaim(memberidClaim, memberId)
								.withClaim(providerClaim, provider)
								.withClaim(roleClaim, role)
								.withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_MSEC))
								.sign(Algorithm.HMAC256(JWT_KEY));
		return prefix + src;
	}
	
	public static String getClaim(String token, String claimName) {
		String tok = getJWTSource(token);
		Claim claim = JWT.require(Algorithm.HMAC256(JWT_KEY)).build().verify(tok).getClaim(claimName);
		if(claim.isMissing())
			return null;
		return claim.asString();
	}
	
	public static boolean isExpired(String token) {
		String tok = getJWTSource(token);
		return JWT.require(Algorithm.HMAC256(JWT_KEY)).build().verify(tok).getExpiresAt().before(new Date());
	}
	
	public static Cookie makeJWTTokenCookie(String token, int timer) {
		String realToken = (token == null)? null : token.replace(JWTUtil.prefix, "");
		
		Cookie cookie = new Cookie("jwtToken", realToken);
		cookie.setHttpOnly(true);
		cookie.setSecure(false);
		cookie.setPath("/");
		cookie.setMaxAge(timer);
		return cookie;
	}
}
