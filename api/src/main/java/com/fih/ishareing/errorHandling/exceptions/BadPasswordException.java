package com.fih.ishareing.errorHandling.exceptions;

import com.fih.ishareing.errorHandling.abs.AbstractException;
import com.fih.ishareing.errorHandling.BaseException;

public class BadPasswordException extends AbstractException implements BaseException {
	private static final long serialVersionUID = 1L;
	private static final String error = "badPassword";
	private static final String description = "Password format is invalid";

	public BadPasswordException() {
		super(error, description, description);
	}

	public BadPasswordException(String msg) {
		super(error, description, msg);
	}

	public BadPasswordException(String msg, Throwable t) {
		super(error, description, msg, t);
	}

}
