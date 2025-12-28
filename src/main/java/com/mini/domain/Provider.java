package com.mini.domain;

public enum Provider {
	LOCAL, GOOGLE, NAVER;
	
	public static Provider findByString(String provider) {
		try {
			return Provider.valueOf(provider.toUpperCase());
		} catch (Exception e) {
			return Provider.LOCAL;
		}	
	}
}
