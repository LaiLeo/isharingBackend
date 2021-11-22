package com.fih.ishareing.errorHandling.exceptions;

import com.fih.ishareing.errorHandling.abs.AbstractException;
import com.fih.ishareing.errorHandling.BaseException;

public class EmailAlreadyValidatedException extends AbstractException implements BaseException {
    private static final long serialVersionUID = -5170398048376226355L;
    private static final String error = "emailAlreadyValidated";
    private static final String description = "User email already had been validated";

    public EmailAlreadyValidatedException() {
        super(error, description, description);
    }

    public EmailAlreadyValidatedException(String msg) {
        super(error, description, msg);
    }

    public EmailAlreadyValidatedException(String msg, Throwable t) {
        super(error, description, msg, t);
    }
}
