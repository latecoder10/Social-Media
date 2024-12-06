package com.tap.social.exception;

public class StoryNotFoundException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StoryNotFoundException(String message) {
		super(message);
	}
}
