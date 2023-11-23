package com.infyfacebook.user.utility;

import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.infyfacebook.user.exception.InfyFacebookException;


@RestControllerAdvice
public class ExceptionControllerAdvice {
	private static final Log LOGGER = LogFactory.getLog(ExceptionControllerAdvice.class);
	@Autowired
	private Environment environment;
	
	@ExceptionHandler(InfyFacebookException.class)
	public ResponseEntity<ErrorInfo> infyFacebookExceptionHandler(InfyFacebookException exception) {
		LOGGER.error(exception.getMessage(), exception);
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
		errorInfo.setErrorMessage(environment.getProperty(exception.getMessage()));
		return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorInfo> generalExceptionHandler(Exception exception) {
		LOGGER.error(exception.getMessage(), exception);
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorInfo.setErrorMessage(environment.getProperty("General.EXCEPTION_MESSAGE"));
		return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler({ MethodArgumentNotValidException.class, ConstraintViolationException.class })
	public ResponseEntity<ErrorInfo> validatorExceptionHandler(Exception exception) {
		LOGGER.error(exception.getMessage(), exception);
		String errorMessage = null;
		if (exception instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
			errorMessage = methodArgumentNotValidException.getBindingResult().getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
		} else if (exception instanceof ConstraintViolationException constraintViolationException) {
			errorMessage = constraintViolationException.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
					.collect(Collectors.joining(", "));
		}
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
		errorInfo.setErrorMessage(errorMessage);
		return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
