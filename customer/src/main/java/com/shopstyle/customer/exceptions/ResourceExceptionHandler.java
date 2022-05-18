package com.shopstyle.customer.exceptions;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;


@ControllerAdvice
public class ResourceExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        StandardError error = new StandardError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                //ex.getBindingResult().getFieldError().getField() + " " + ex.getBindingResult().getFieldError().getDefaultMessage(),
                ex.getFieldError().getField() + " " + ex.getFieldError().getDefaultMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> httpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        JsonMappingException jme = (JsonMappingException) ex.getCause();
        String campo = jme.getPath().get(0).getFieldName();
        String msg2 = "";
        String msg = "";
        if (campo.equals("sex")) {
            msg = "Valid values: Masculino || Feminino";
        } else if (campo.equals("birthdate")) {
            msg = "Format valid: dd/MM/yyyy";
        }
        StandardError error = new StandardError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "camp: " + campo + " invalid. " + msg,
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> httpMessageNotReadableException(DataIntegrityViolationException ex, HttpServletRequest request) {
        var exceptionCause = ex.getMostSpecificCause();
        if (exceptionCause instanceof PSQLException psqlException) {
            var serverErrorMessage = psqlException.getServerErrorMessage();
            if (serverErrorMessage != null) {
                var detail = serverErrorMessage.getDetail();
                exceptionCause = new RuntimeException(detail);
            }
        }
        StandardError error = new StandardError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                exceptionCause.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<StandardError> methodArgumentNotValidException(NoSuchElementException ex, HttpServletRequest request) {

        StandardError error = new StandardError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
