package com.fih.ishareing.errorHandling.exceptions;

import com.fih.ishareing.errorHandling.ErrorHandlingConstants;
import com.fih.ishareing.errorHandling.abs.AbstractException;
import com.fih.ishareing.errorHandling.BaseException;

public class QuotaLimitationException extends AbstractException implements BaseException {
    private static final long serialVersionUID = 1L;

    private static final String error = ErrorHandlingConstants.ERROR_BUNDLE.quotaLimitation.name();
    private static final String description = ErrorHandlingConstants.ERROR_BUNDLE.quotaLimitation.getDesc();

    public QuotaLimitationException() {
        super(error, description, description);
    }

    public QuotaLimitationException(String msg) {
        super(error, description, msg);
    }

    public QuotaLimitationException(String msg, Throwable t) {
        super(error, description, msg, t);
    }
}