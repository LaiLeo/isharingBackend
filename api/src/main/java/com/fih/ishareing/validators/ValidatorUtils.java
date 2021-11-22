package com.fih.ishareing.validators;


import com.fih.ishareing.errorHandling.exceptions.ParameterException;
import com.fih.ishareing.errorHandling.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ValidatorUtils {
	private static final Logger logger = LoggerFactory.getLogger(ValidatorUtils.class);
    private static final Validator javaxValidator = Validation.buildDefaultValidatorFactory().getValidator();
    private static final SpringValidatorAdapter validator = new SpringValidatorAdapter(javaxValidator);

    public static Errors validate(Object entry) {
        Errors errors = new BeanPropertyBindingResult(entry, entry.getClass().getName());
        validator.validate(entry, errors);
        if (errors == null || errors.getAllErrors().isEmpty())
            return null;
        else {
            logger.error(errors.toString());
            return errors;
        }
    }
    
    public static List<BaseException> convertValidErrorsToBaseException(Errors errors) {
    	List<BaseException> list = new ArrayList<BaseException>();
    	if (errors != null && !CollectionUtils.isEmpty(errors.getAllErrors())) {
    		list.addAll(errors.getAllErrors().stream().map(e -> {
    			String fieldName = "";
    			if(e instanceof FieldError) {
    		        FieldError fieldError = (FieldError) e;
    		        fieldName = fieldError.getField() + " ";
    		    }
    			ParameterException eVo = new ParameterException(fieldName + e.getDefaultMessage());
    			return eVo;
    		}).collect(Collectors.toList()));
    	}
		
		return list;
	}
}
