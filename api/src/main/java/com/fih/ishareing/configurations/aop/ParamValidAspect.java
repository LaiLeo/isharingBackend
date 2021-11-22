package com.fih.ishareing.configurations.aop;

import java.util.Optional;

import com.google.common.base.Preconditions;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Aspect
@Component
public class ParamValidAspect {

	private static final Logger logger = LoggerFactory.getLogger(ParamValidAspect.class);

	@Pointcut("execution(public * com.fih.ishareing.controller..*(..))")
	public void atRestController() {
	}

	@Before("atRestController()")
	public void checkParamer(JoinPoint point) {
		logger.debug("Check parameter valid");
		if (point.getArgs().length > 0) {
			for (Object paramObj : point.getArgs()) {
				if (paramObj instanceof BindingResult) {
					BindingResult result = (BindingResult) paramObj;

					Preconditions.checkArgument(!result.hasErrors(), result.hasErrors() ? toMessage(result) : "");

					// should be one BindingResult in api, don't loop more
					break;
				}
			}
		}
	}

	private String toMessage(BindingResult result) {
		String field = "";
		Optional<ObjectError> error = result.getAllErrors().stream().findFirst();
		if (error.isPresent()) {
			for (Object obj : error.get().getArguments()) {
				if (obj instanceof MessageSourceResolvable) {
					MessageSourceResolvable m = (MessageSourceResolvable) obj;
					field = m.getDefaultMessage();
					break;
				}
			}

			return String.format("%s %s", field, error.get().getDefaultMessage()).trim();
		}

		return "internal_error";
	}

}
