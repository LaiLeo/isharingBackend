package com.fih.ishareing.errorHandling.exceptions;

import com.fih.ishareing.errorHandling.abs.AbstractException;
import com.fih.ishareing.errorHandling.BaseException;

public class BadDateException extends AbstractException implements BaseException {
	private static final long serialVersionUID = 1L;
	private static final String error = "badDate";
	private static final String description = "Date is invalid";

	public BadDateException() {
		super(error, description, description);
	}

	public BadDateException(String msg) {
		super(error, description, msg);
	}

	public BadDateException(String msg, Throwable t) {
		super(error, description, msg, t);
	}

}
