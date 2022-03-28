package com.galaxia.engdev.exception;

import static com.galaxia.engdev.errorcode.NeptuneErrorCode.UNKNOWN_ERROR;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.galaxia.engdev.errorcode.ErrorCode;

@RestController
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		NeptuneException er = new NeptuneException(UNKNOWN_ERROR);

		return new ResponseEntity<Object>(er, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ErrorCode neptuneErrorCode = new ErrorCode(UNKNOWN_ERROR);
		neptuneErrorCode.setResponseMsg("필수값 오류");
		neptuneErrorCode.setDetailMsg(ex.getMessage());

		NeptuneException er = new NeptuneException(neptuneErrorCode);

		return new ResponseEntity<Object>(er, HttpStatus.BAD_REQUEST);
	}
}
