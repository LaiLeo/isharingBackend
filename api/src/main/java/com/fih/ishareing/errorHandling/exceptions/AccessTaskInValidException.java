package com.fih.ishareing.errorHandling.exceptions;

import com.fih.ishareing.errorHandling.abs.AbstractException;
import com.fih.ishareing.errorHandling.BaseException;

public class AccessTaskInValidException extends AbstractException implements BaseException {
	private static final long serialVersionUID = 1L;

	private static final String error = "invalidAccessTask";
	private static final String description = "Access task is out-of-date or invalid";

	public AccessTaskInValidException() {
		super(error, description, description);
	}

	public AccessTaskInValidException(String msg) {
		super(error, description, msg);
	}

	public AccessTaskInValidException(String msg, Throwable t) {
		super(error, description, msg, t);
	}

}
