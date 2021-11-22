package com.fih.ishareing.errorHandling.exceptions;

import com.fih.ishareing.errorHandling.abs.AbstractException;
import com.fih.ishareing.errorHandling.BaseException;

public class InvalidUserPasswordException extends AbstractException implements BaseException {
	private static final long serialVersionUID = 1L;

	private static final String error = "invalidUserOrPassword";
	private static final String description = "User or password is invalid";

	public InvalidUserPasswordException() {
		super(error, description, description);
	}

	public InvalidUserPasswordException(String msg) {
		super(error, description, msg);
	}

	public InvalidUserPasswordException(String msg, Throwable t) {
		super(error, description, msg, t);
	}

}
