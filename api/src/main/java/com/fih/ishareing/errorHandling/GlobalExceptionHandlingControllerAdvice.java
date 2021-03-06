package com.fih.ishareing.errorHandling;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fih.ishareing.controller.base.error.exception.ExceptionResponseVO;
import com.fih.ishareing.errorHandling.base.ResourceConflictException;
import com.fih.ishareing.errorHandling.base.ResourceNotFoundException;
import com.fih.ishareing.errorHandling.base.ServiceUnavailableException;
import com.fih.ishareing.errorHandling.exceptions.*;
import com.fih.ishareing.utils.ErrorUtil;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.MethodNotAllowedException;


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
	 * Http Code : 200
	 * */
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ExceptionHandler({
			InValidRefreshTokenException.class,
			InvalidUserPasswordException.class,
			UserNotFoundException.class,
			BannerNotFoundException.class,
			ImageTypeInValidException.class,
			ImageScaleToConfigInValidException.class,
			FileImageTypeInValidException.class,
			FileSizeLimitExceededException.class,
			ImageSizeInValidException.class,
			UserLicenseImageNotFoundException.class
	})
	ResponseEntity<ExceptionResponseVO> handleOK(Exception e) {
		return new ResponseEntity<ExceptionResponseVO>(processErrorMessage(e), HttpStatus.OK);
	}

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
						BlackListException.class,
						MissingPathVariableException.class,
						MethodArgumentTypeMismatchException.class,
						NumberFormatException.class})
	ResponseEntity<ExceptionResponseVO> handleBadRequest(Exception e) {
		if (e instanceof MissingServletRequestParameterException || 
			e instanceof UnsatisfiedServletRequestParameterException ||
		 	e instanceof IllegalArgumentException ||
			e instanceof MissingPathVariableException ||
		    e instanceof MethodArgumentTypeMismatchException ||
			e instanceof NumberFormatException) {
			e = new ParameterException(e.getMessage());
		}
		return new ResponseEntity<ExceptionResponseVO>(processErrorMessage(e), HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Http Code : 404
	 * */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({	ResourceNotFoundException.class})
	@ResponseBody
	ExceptionResponseVO handleNotFound(Exception e) {
		return processErrorMessage(e);
	}

	/**
	 * Http Code : 405
	 * */
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler({	HttpRequestMethodNotSupportedException.class, MethodNotAllowedException.class})
	@ResponseBody
	ExceptionResponseVO handleMethodNotAllowed(Exception e) {
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
					   	InternalErrorException.class,
						NullPointerException.class})
	@ResponseBody
	ResponseEntity<ExceptionResponseVO> handleInteralServerError(Exception e) {
		Throwable cause = e.getCause();
		
		if (e instanceof InvalidFormatException || cause instanceof InvalidFormatException
		 || e instanceof IllegalArgumentException || cause instanceof NumberFormatException) {
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
		} else if (e.getCause() instanceof InvalidFormatException || e.getCause() instanceof NumberFormatException) {
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
