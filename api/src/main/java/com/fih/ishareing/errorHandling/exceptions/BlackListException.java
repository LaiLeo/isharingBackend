package com.fih.ishareing.errorHandling.exceptions;

import com.fih.ishareing.errorHandling.abs.AbstractException;
import com.fih.ishareing.errorHandling.BaseException;

import java.io.Serializable;

public class BlackListException extends AbstractException implements BaseException,Serializable {
	private static final long serialVersionUID = 1L;
	private static final String error = "tooManyRequests";
	private static final String description = "The number of requests exceeds the limit.";

	public BlackListException() {
		super(error, description, description);
	}

	public BlackListException(String msg) {
		super(error, description,msg);
	}

	public BlackListException(String msg, Throwable t) {
		super(error, description, msg, t);
	}

}
