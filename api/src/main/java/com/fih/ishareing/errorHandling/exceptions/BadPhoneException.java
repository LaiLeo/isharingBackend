package com.fih.ishareing.errorHandling.exceptions;

import com.fih.ishareing.errorHandling.BaseException;
import com.fih.ishareing.errorHandling.abs.AbstractException;

import java.io.Serializable;

public class BadPhoneException extends AbstractException implements BaseException,Serializable {
	private static final long serialVersionUID = 1L;
	private static final String error = "badPhone";
	private static final String description = "Phone format is invalid";
	
	public BadPhoneException() {
		super(error, description, description);
	}
	
	public BadPhoneException(String msg) {
		super(error, description,msg);
	}

	public BadPhoneException(String msg, Throwable t) {
		super(error, description, msg, t);
	}

}
