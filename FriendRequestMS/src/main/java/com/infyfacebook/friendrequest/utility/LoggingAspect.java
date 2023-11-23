package com.infyfacebook.friendrequest.utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.infyfacebook.friendrequest.exception.InfyFacebookException;

@Aspect
@Component
public class LoggingAspect {
	private static final Log LOGGER = LogFactory.getLog(LoggingAspect.class);
	@AfterThrowing(pointcut = "execution(* com.infyfacebook.friendrequest.service.*Impl.*(..))", throwing = "exception")
	public void logServiceException(InfyFacebookException exception) {
		LOGGER.error(exception.getMessage(), exception);
	}
}
