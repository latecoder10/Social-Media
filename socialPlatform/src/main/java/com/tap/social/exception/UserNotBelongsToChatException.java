package com.tap.social.exception;

public class UserNotBelongsToChatException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotBelongsToChatException(String message) {
		super(message);
	}
}
