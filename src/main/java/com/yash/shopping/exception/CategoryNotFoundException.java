package com.yash.shopping.exception;

/**
 * @author yash.ghawghawe
 *
 */
public class CategoryNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	private final String str;

	public CategoryNotFoundException(String str) {
		this.str = str;
	}

	@Override
	public String toString() {
		return str;
	}

}
