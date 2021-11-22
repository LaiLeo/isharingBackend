package com.fih.ishareing.validators;

import org.springframework.stereotype.Component;

@Component
public class CodeValidator extends RegexValidator {
    public static final String CODE_PATTERN ="[@:_\\-\\$a-zA-Z0-9]+";
    public CodeValidator() {
        super(CODE_PATTERN);
    }
}