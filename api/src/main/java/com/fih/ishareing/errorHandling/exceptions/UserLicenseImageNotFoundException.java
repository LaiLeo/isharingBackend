package com.fih.ishareing.errorHandling.exceptions;

import com.fih.ishareing.errorHandling.BaseException;
import com.fih.ishareing.errorHandling.abs.AbstractException;

public class UserLicenseImageNotFoundException extends AbstractException implements BaseException {
	private static final long serialVersionUID = 1L;

	private static final String error = "userLicenseImageNotFound";
	private static final String description = "User license image not exist";

	public UserLicenseImageNotFoundException() {
		super(error, description, description);
	}

	public UserLicenseImageNotFoundException(String msg) {
		super(error, description, msg);
	}

	public UserLicenseImageNotFoundException(String msg, Throwable t) {
		super(error, description, msg, t);
	}

}
