package com.fih.ishareing.errorHandling.exceptions;

import com.fih.ishareing.errorHandling.abs.AbstractException;
import com.fih.ishareing.errorHandling.BaseException;

public class PasswordInValidException extends AbstractException implements BaseException {
	private static final long serialVersionUID = 1L;

	private static final String error = "invalidPassword";
	private static final String description = "Password is out-of-date or invalid";

	public PasswordInValidException() {
		super(error, description, description);
	}

	public PasswordInValidException(String msg) {
		super(error, description, msg);
	}

	public PasswordInValidException(String msg, Throwable t) {
		super(error, description, msg, t);
	}
}
