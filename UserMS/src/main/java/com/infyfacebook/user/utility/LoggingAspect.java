package com.infyfacebook.user.utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.infyfacebook.user.exception.InfyFacebookException;

@Aspect
@Component 
public class LoggingAspect {
	
	private static final Log LOGGER = LogFactory.getLog(LoggingAspect.class);
	@AfterThrowing(pointcut = "execution(* com.infyfacebook.user.service.*Impl.*(..))", throwing = "exception")
	public void logServiceException(InfyFacebookException exception) {
		LOGGER.error(exception.getMessage(), exception);
	}
}