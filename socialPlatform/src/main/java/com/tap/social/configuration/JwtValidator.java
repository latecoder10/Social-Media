package com.tap.social.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tap.social.exception.JwtAuthenticationException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidator extends OncePerRequestFilter {
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String jwt = request.getHeader(JwtConstant.JWT_HEADER);
		
		if(jwt!=null) {
			try {
				
				String email=JwtProvider.getEmailFromJwtToken(jwt);
				
				List<GrantedAuthority> authorities = new ArrayList<>();
				
				Authentication authenticationToken = new UsernamePasswordAuthenticationToken(email,null, authorities);
				
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			} catch (Exception e) {
//				throw new JwtAuthenticationException("Invalid Token!"); // Use the custom exception
				// Instead of throwing an exception, you can set a response status
	            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token!");
	            return; // Stop further processing

			}
		}
		
		filterChain.doFilter(request, response);
		
	}

	

}
