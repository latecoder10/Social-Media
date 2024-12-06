package com.tap.social.exception;

public class JwtAuthenticationException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JwtAuthenticationException(String message) {
		super(message);
	}
}
