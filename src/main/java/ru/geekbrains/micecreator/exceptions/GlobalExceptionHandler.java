package ru.geekbrains.micecreator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<?> catchBadInputException(BadInputException e) {
		return new ResponseEntity<>(new ErrorContent(e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<?> catchDataNotFoundException(DataNotFoundException e) {
		return new ResponseEntity<>(new ErrorContent(e.getMessage()), HttpStatus.NOT_FOUND);
	}
}
