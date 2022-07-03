package com.plats.allinoutservice.accountservice.exceptionhandler;

import com.plats.allinoutservice.accountservice.exceptions.AccountCredentialsNotAvailableException;
import com.plats.allinoutservice.accountservice.exceptions.WrongAccountCredentialsException;
import com.plats.allinoutservice.accountservice.pojo.ErrorResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;
import java.util.logging.Logger;

@RestControllerAdvice
public class AccountExceptionsHandler {

    private Logger logger = Logger.getLogger(AccountExceptionsHandler.class.getName());

    @ExceptionHandler(WrongAccountCredentialsException.class)
    public ResponseEntity<ErrorResponse> wrongAccountCredentialsResponse(Exception ex) {
        logError(ex);
        ErrorResponse errorResponse = new ErrorResponse("Wrong account credentials.");

        String requestId = UUID.randomUUID().toString();

        return exceptionsReturn(requestId, errorResponse);
    }

    @ExceptionHandler(AccountCredentialsNotAvailableException.class)
    public ResponseEntity<ErrorResponse> unidentifiedCredentialsErrorResponse(Exception ex) {
        logError(ex);
        ErrorResponse errorResponse = new ErrorResponse("Account credentials not available.");

        String requestId = UUID.randomUUID().toString();

        return exceptionsReturn(requestId, errorResponse);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> unknownDataErrorResponse(Exception ex) {
        logError(ex);
        ErrorResponse errorResponse = new ErrorResponse("Unknown data error.");

        String requestId = UUID.randomUUID().toString();
        System.out.println(ex);

        return exceptionsReturn(requestId, errorResponse);
    }

    private ResponseEntity<ErrorResponse> exceptionsReturn(String requestId, ErrorResponse errorResponse) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header("request_id", requestId)
                .body(errorResponse);
    }

    private void logError(Exception ex) {
        logger.warning("Error detected, exceptions: " + ex.getClass().getName());
    }
}
