package com.fih.ishareing.errorHandling;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fih.ishareing.controller.base.error.exception.ExceptionResponseVO;
import com.fih.ishareing.errorHandling.base.ResourceConflictException;
import com.fih.ishareing.errorHandling.base.ResourceNotFoundException;
import com.fih.ishareing.errorHandling.base.ServiceUnavailableException;
import com.fih.ishareing.errorHandling.exceptions.*;
import com.fih.ishareing.utils.ErrorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * The global error handling. Mapping HTTP Status Code with customized exceptions.
 * 
 * @author gregkckuo
 * */
@ControllerAdvice
public class GlobalExceptionHandlingControllerAdvice {

	private static final Logger logger = LoggerFactory
			.getLogger(GlobalExceptionHandlingControllerAdvice.class);
	
	/**
	 * Http Code : 400
	 * */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	@ExceptionHandler({	HttpMessageNotReadableException.class,
						MissingServletRequestParameterException.class,
						UnsatisfiedServletRequestParameterException.class,
						ParameterException.class,
						SignatureInValidException.class,
						TokenInValidException.class,
						PasswordInValidException.class,
						BadDateException.class,
						BadPasswordException.class,
						BadIntervalFormatException.class,
						BeanCreationException.class,
						AccessTaskInValidException.class,
						EmailAddressFailedException.class,
						TooManyResourcesException.class,
						InValidRefreshTokenException.class,
						BlackListException.class,
						InvalidUserPasswordException.class})
	ResponseEntity<ExceptionResponseVO> handleBadRequest(Exception e) {
		if (e instanceof MissingServletRequestParameterException || 
			e instanceof UnsatisfiedServletRequestParameterException ||
		 	e instanceof IllegalArgumentException) {
			e = new ParameterException(e.getMessage());
		}
		return new ResponseEntity<ExceptionResponseVO>(processErrorMessage(e), HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Http Code : 404
	 * */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({	ResourceNotFoundException.class,
						UserNotFoundException.class})
	@ResponseBody
	ExceptionResponseVO handleNotFound(Exception e) {
		return processErrorMessage(e);
	}
	
	/**
	 * Http Code : 409
	 * */
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler({ EmailAlreadyValidatedException.class,
						ResourceConflictException.class,
						DataIntegrityViolationException.class,
						ForeignResourceConflictsException.class})
	@ResponseBody
	ExceptionResponseVO handleConflict(Exception e) {
		return processErrorMessage(e);
	}

	/**
	 * Http Code : 422
	 */
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler({ BadEmailException.class,
						BadFileNameException.class,
						BadPhoneException.class,
						PasswordNotMatchException.class,
						PasswordTheSameException.class
	})
	@ResponseBody
	ExceptionResponseVO handleUnprocessableEntity(Exception e) {
		return processErrorMessage(e);
	}

	/**
	 * Http Code : 429
	 * */
	@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
	@ExceptionHandler({ RequestUnavailableException.class, QuotaLimitationException.class })
	@ResponseBody
	ExceptionResponseVO handleToManyRequest(Exception e) {
		return processErrorMessage(e);
	}
	
	/**
	 * Http Code : 500
	 * UnHandle Exception (Exception.class) handled by this method.
	 * */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({	Exception.class,
					   	InternalErrorException.class})
	@ResponseBody
	ResponseEntity<ExceptionResponseVO> handleInteralServerError(Exception e) {
		Throwable cause = e.getCause();
		
		if (e instanceof InvalidFormatException || cause instanceof InvalidFormatException
		 || e instanceof IllegalArgumentException) {
			return handleBadRequest(e);
		}
		return new ResponseEntity<ExceptionResponseVO>(processErrorMessage(e), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * Http Code : 503
	 * */
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	@ExceptionHandler({ PasswordUnavailableException.class,
						ServiceUnavailableException.class})
	@ResponseBody
	ResponseEntity<ExceptionResponseVO> handleServiceUnavailable(Exception e) {
		return new ResponseEntity<ExceptionResponseVO>(processErrorMessage(e), HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	private ExceptionResponseVO processErrorMessage(Exception e) {
		ExceptionResponseVO vo = new ExceptionResponseVO();
		if (e instanceof BaseException){
			vo.setError(((BaseException)e).getError());
			vo.setDescription(((BaseException)e).getDescription());
		} else if (e.getCause() instanceof InvalidFormatException) {
			vo.setError(ParameterException.error);
			vo.setDescription(e.getCause().getMessage());
		} else {
			vo.setError(ErrorHandlingConstants.ERROR_BUNDLE.internalError.name());
			vo.setDescription(ErrorUtil.errorTrackString(e));
			logger.error("UnHandling Exception", e);
		}
		return vo;
	}
	
}
