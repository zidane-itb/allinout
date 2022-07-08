package com.plats.allinoutservice.accountservice.exceptionhandler;

import com.plats.allinoutservice.accountservice.exceptions.*;
import com.plats.allinoutservice.accountservice.pojo.ErrorResponse;
import com.plats.allinoutservice.accountservice.pojo.Secret;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@RestControllerAdvice
@Log4j2
public class AccountExceptionsHandler {

    @ExceptionHandler(WrongAccountCredentialsException.class)
    public ResponseEntity<ErrorResponse> wrongAccountCredentialsResponse(Exception ex) {
        logError(ex);
        ErrorResponse errorResponse = new ErrorResponse("Wrong account credentials.");

        return exceptionsReturn(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> unknownDataErrorResponse(Exception ex) {
        logError(ex);
        ErrorResponse errorResponse = new ErrorResponse("Unknown data error.");

        return exceptionsReturn(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountInvalidException.class)
    public ResponseEntity<ErrorResponse> accountInvalidExceptionResponse(Exception ex) {
        logError(ex);
        ErrorResponse errorResponse = new ErrorResponse("Account secret string invalid.");

        return exceptionsReturn(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AlreadyLoggedInException.class)
    public ResponseEntity<ErrorResponse> alreadyLoggedInExceptionResponse(Exception ex) {
        logError(ex);
        ErrorResponse errorResponse = new ErrorResponse("Account you try to login already " +
                                                                "have an active session.");

        return exceptionsReturn(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotAdminException.class)
    public ResponseEntity<ErrorResponse> notAdminExceptionResponse(Exception ex) {
        logError(ex);
        ErrorResponse errorResponse = new ErrorResponse("You're accessing admin feature without admin access.");

        return exceptionsReturn(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SecretStringExpiredException.class)
    public ResponseEntity<ErrorResponse> secretStringExpiredExceptionResponse(Exception ex) {
        logError(ex);
        ErrorResponse errorResponse = new ErrorResponse("You're using an expired session.");

        return exceptionsReturn(errorResponse, HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<ErrorResponse> exceptionsReturn(ErrorResponse errorResponse, HttpStatus status) {
        String requestId = UUID.randomUUID().toString();
        return ResponseEntity
                .status(status)
                .header("request_id", requestId)
                .body(errorResponse);
    }

    private void logError(Exception ex) {
        log.warn("Error detected, exceptions: " + ex.getClass().getName());
    }
}
