package com.alten.shop.service.exception;

/**
 * Exception class for user already exists by username
 * @Autor Dénez Fauchon
 */
public class UserAlreadyExistsException extends Exception {
	public UserAlreadyExistsException(String format) {
		super(format);
	}
}
