package com.fih.ishareing.errorHandling.exceptions;

import com.fih.ishareing.errorHandling.abs.AbstractException;
import com.fih.ishareing.errorHandling.BaseException;

public class UserNotFoundException extends AbstractException implements BaseException {
	private static final long serialVersionUID = 1L;

	private static final String error = "userNotFound";
	private static final String description = "User not exist";

	public UserNotFoundException() {
		super(error, description, description);
	}

	public UserNotFoundException(String msg) {
		super(error, description, msg);
	}

	public UserNotFoundException(String msg, Throwable t) {
		super(error, description, msg, t);
	}

}
