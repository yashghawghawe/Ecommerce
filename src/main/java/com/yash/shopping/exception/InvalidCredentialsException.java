package com.yash.shopping.exception;

/**
 * @author yash.ghawghawe
 *
 */
public class InvalidCredentialsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String str;

	public InvalidCredentialsException(String str) {
		this.str = str;
	}

	@Override
	public String toString() {
		return str;
	}

}
