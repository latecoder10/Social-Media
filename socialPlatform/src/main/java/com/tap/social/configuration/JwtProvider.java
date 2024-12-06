package com.tap.social.configuration;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {
	private static SecretKey key=Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
	
	public static String generateToken(Authentication auth) {
		
		String jwt=Jwts.builder()
					.setIssuer("latecoder10")
					.setIssuedAt(new Date())
					.setExpiration(new Date(new Date().getTime()+86400000))
					.claim("email", auth.getName())
					.signWith(key)
					.compact();
		return jwt;
	}
	
	
	public static String getEmailFromJwtToken(String jwt) {
	    try {
	        if (jwt.startsWith("Bearer ")) {
	            jwt = jwt.substring(7);
	        } else {
	            throw new IllegalArgumentException("Invalid JWT format");
	        }
	        Claims body = Jwts.parserBuilder()
	            .setSigningKey(key)
	            .build()
	            .parseClaimsJws(jwt)
	            .getBody();
	        return String.valueOf(body.get("email"));
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to parse JWT: " + e.getMessage(), e);
	    }
	}

}
