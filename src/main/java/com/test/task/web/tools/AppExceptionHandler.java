package com.test.task.web.tools;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.test.task.common.exceptions.currency.CurrencyMappingException;
import com.test.task.common.exceptions.type.TypedError;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class AppExceptionHandler {

    @ExceptionHandler(CurrencyMappingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected TypedError handleCurrencyMappingException(CurrencyMappingException ex) {
        log.error("Currency mapping", ex.getMessage());
        return ex;
    }
}
