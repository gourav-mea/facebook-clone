package com.infyfacebook.posts.utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.infyfacebook.posts.exception.InfyFacebookException;


@Aspect
@Component 
public class LoggingAspect {
	
	private static final Log LOGGER = LogFactory.getLog(LoggingAspect.class);
	@AfterThrowing(pointcut = "execution(* com.infyfacebook.posts.service.*Impl.*(..))", throwing = "exception")
	public void logServiceException(InfyFacebookException exception) {
		LOGGER.error(exception.getMessage(), exception);
	}
}