package com.yash.shopping.exception;

public class InsufficientBalanceException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private final String str;

	public InsufficientBalanceException(String str) {
		this.str = str;
	}

	@Override
	public String toString() {
		return str;
	}

}
