package com.fih.ishareing.errorHandling.exceptions;

import com.fih.ishareing.errorHandling.abs.AbstractException;
import com.fih.ishareing.errorHandling.BaseException;

public class BannerNotFoundException extends AbstractException implements BaseException {
	private static final long serialVersionUID = 1L;

	private static final String error = "bannerNotFound";
	private static final String description = "Banner not exist";

	public BannerNotFoundException() {
		super(error, description, description);
	}

	public BannerNotFoundException(String msg) {
		super(error, description, msg);
	}

	public BannerNotFoundException(String msg, Throwable t) {
		super(error, description, msg, t);
	}

}
