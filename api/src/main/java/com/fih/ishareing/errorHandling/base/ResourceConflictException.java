package com.fih.ishareing.errorHandling.base;

import com.fih.ishareing.errorHandling.ErrorHandlingConstants;
import com.fih.ishareing.errorHandling.abs.AbstractException;
import com.fih.ishareing.errorHandling.BaseException;

public class ResourceConflictException extends AbstractException implements BaseException {
	private static final long serialVersionUID = 1L;

	public ResourceConflictException(ErrorHandlingConstants.ERROR_BUNDLE errorBundle) {
		super(errorBundle.name(), errorBundle.getDesc(), "");
	}

	public ResourceConflictException(ErrorHandlingConstants.ERROR_BUNDLE errorBundle, String msg) {
		super(errorBundle.name(), errorBundle.getDesc(), msg);
	}

	public ResourceConflictException(ErrorHandlingConstants.ERROR_BUNDLE errorBundle, String msg, Throwable t) {
		super(errorBundle.name(), errorBundle.getDesc(), msg, t);
	}

}
