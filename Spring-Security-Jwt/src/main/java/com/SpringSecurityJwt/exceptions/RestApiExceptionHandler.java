package com.SpringSecurityJwt.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestApiExceptionHandler{

	
	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}
	

	@ExceptionHandler({Throwable.class})
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler({AlreadyExistsException.class})
    public ResponseEntity<Object> handleAlreadyExistsException(Exception ex,WebRequest request) {
        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.CONFLICT);
    }
	
	@ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(Exception ex,WebRequest request) {
        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler({UserNameNotFoundException.class})
    public ResponseEntity<Object> handleUserNameNotFoundException(Exception ex,WebRequest request) {
        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String,String> handleInvalidArgument(MethodArgumentNotValidException ex)
	{
		Map<String,String> map=new HashMap<>();
		
		ex.getBindingResult().getFieldErrors().forEach(error->{
		
		map.put(error.getField(), error.getDefaultMessage());
		
		});
		
		return map;
	}
}
