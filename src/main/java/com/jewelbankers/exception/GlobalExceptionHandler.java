package com.jewelbankers.exception;

import java.time.format.DateTimeFormatter;

import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.jewelbankers.Utility.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	 @InitBinder
	    public void initBinder(WebDataBinder binder) {
		 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
	    }
	 
	@ExceptionHandler(JpaSystemException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleJpaSystemException(JpaSystemException ex) {
		ex.printStackTrace();
		ErrorResponse errorResponse = new ErrorResponse(
                "System error occurred! Please try after sometime",
                ex.getMessage()
        );
		  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
	
	@ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
		ex.printStackTrace();
        ErrorResponse errorResponse = new ErrorResponse(
                "System error occurred! Please try after sometime",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    // Add more specific exception handlers if needed
}
