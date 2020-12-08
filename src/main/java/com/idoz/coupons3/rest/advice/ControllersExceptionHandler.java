package com.idoz.coupons3.rest.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.idoz.coupons3.exceptions.DataManipulationException;
import com.idoz.coupons3.exceptions.DetailDuplicationException;
import com.idoz.coupons3.exceptions.WrongIdException;
import com.idoz.coupons3.rest.beans.ErrorMessage;

@ControllerAdvice
public class ControllersExceptionHandler {
	
	@ExceptionHandler({ WrongIdException.class, DetailDuplicationException.class, DataManipulationException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorMessage> handleException(Exception e) {
		return new ResponseEntity<>(new ErrorMessage(e),HttpStatus.BAD_REQUEST);
	}

}
