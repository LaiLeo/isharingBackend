package com.fih.ishareing.controller.base.error.exception;

import com.fih.ishareing.errorHandling.BaseException;
import com.fih.ishareing.errorHandling.abs.AbstractException;

public class ParameterException extends AbstractException implements BaseException {
	private static final long serialVersionUID = 1L;
	public static final String error = "invalidRequest";
	private static final String description = "Parameter is invalid or cannot be recognized";

	public ParameterException() {
		super(error, description, description);
	}

	public ParameterException(String msg) {
		super(error, description, msg);
	}

	public ParameterException(String msg, Throwable t) {
		super(error, description, msg, t);
	}

}
