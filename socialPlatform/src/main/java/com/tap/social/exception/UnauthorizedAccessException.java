package com.tap.social.exception;

public class UnauthorizedAccessException extends RuntimeException {
	public UnauthorizedAccessException(String message) {
		super(message);
	}
}
