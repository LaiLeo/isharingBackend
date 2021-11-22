package com.fih.ishareing.errorHandling.exceptions;

import com.fih.ishareing.errorHandling.abs.AbstractException;
import com.fih.ishareing.errorHandling.BaseException;
import com.fih.ishareing.utils.ErrorUtil;

public class InternalErrorException extends AbstractException implements BaseException {
	private static final long serialVersionUID = 1L;
	private static final String error = "internalError";
	private static final String description = "Internal error";

	public InternalErrorException() {
		super(error, description, description);
	}

	public InternalErrorException(String msg) {
		super(error, description, msg);
	}

	public InternalErrorException(String msg, Throwable t) {
		super(error, description, ErrorUtil.errorTrackString(t), t);
	}

}
