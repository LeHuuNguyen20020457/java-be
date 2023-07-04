package com.luv2code.demo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StudentRestExceptionHandler {

    // add exception handling code here
    @ExceptionHandler
    public ResponseEntity<StudentErrorReponse> handleException(StudentNotFoundException exc){
        StudentErrorReponse error = new StudentErrorReponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // add exception cho moi ngoai le
    @ExceptionHandler
    public ResponseEntity<StudentErrorReponse> handleException(Exception exc) {

        StudentErrorReponse error = new StudentErrorReponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value()); //400
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
