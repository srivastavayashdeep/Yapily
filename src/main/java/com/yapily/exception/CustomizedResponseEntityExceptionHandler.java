package com.yapily.exception;

import static java.time.LocalDate.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.yapily.model.ErrorDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomizedResponseEntityExceptionHandler.class);

    @ExceptionHandler(ValidationException.class)
    public final ResponseEntity<ErrorDetails> handleValidationException(Exception ex, WebRequest request) {
        logger.error("Exception Occurred ", ex);
        ErrorDetails errorDetails = getErrorDetails(request, ex.getMessage());
        return new ResponseEntity<>(errorDetails, BAD_REQUEST);
    }

    @ExceptionHandler(CharacterNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleCharacterNotFoundException(Exception ex, WebRequest request) {
        logger.error("Exception Occurred ", ex);
        ErrorDetails errorDetails = getErrorDetails(request, ex.getMessage());
        return new ResponseEntity<>(errorDetails, BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
        logger.error("Exception Occurred ", ex);
        ErrorDetails errorDetails = getErrorDetails(request, ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorDetails getErrorDetails(WebRequest request, String message) {
        return new ErrorDetails(now(), message,
                request.getDescription(false));
    }

}