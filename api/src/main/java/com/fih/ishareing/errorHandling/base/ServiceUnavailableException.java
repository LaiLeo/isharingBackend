package com.fih.ishareing.errorHandling.base;

import com.fih.ishareing.errorHandling.BaseException;
import com.fih.ishareing.errorHandling.ErrorHandlingConstants.ERROR_BUNDLE;
import com.fih.ishareing.errorHandling.abs.AbstractException;

public class ServiceUnavailableException extends AbstractException implements BaseException {
	private static final long serialVersionUID = 1L;

	public ServiceUnavailableException(ERROR_BUNDLE errorBundle) {
		super(errorBundle.name(), errorBundle.getDesc(), "");
	}

	public ServiceUnavailableException(ERROR_BUNDLE errorBundle, String msg) {
		super(errorBundle.name(), errorBundle.getDesc(), msg);
	}

	public ServiceUnavailableException(ERROR_BUNDLE errorBundle, String msg, Throwable t) {
		super(errorBundle.name(), errorBundle.getDesc(), msg, t);
	}

}
