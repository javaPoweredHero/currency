package com.test.task.web.tools;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.test.task.common.exceptions.currency.CurrencyDynamicMappingException;
import com.test.task.common.exceptions.currency.CurrencyMappingException;
import com.test.task.common.exceptions.type.TypedError;
import com.test.task.common.exceptions.type.TypedErrorImpl;

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

    @ExceptionHandler(CurrencyDynamicMappingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected TypedError handleCurrencyDynamicMappingException(CurrencyDynamicMappingException ex) {
        log.error("Currency dynamic mapping", ex.getMessage());
        return ex;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected TypedError handleIllegalArgument(IllegalArgumentException ex) {
        log.error("illegal argument exception", ex.getMessage());
        return new TypedErrorImpl(ex.getClass().getSimpleName(), ex.getMessage());
    }

    @ExceptionHandler(ConversionFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected TypedError handleConversionException(ConversionFailedException ex) {
        log.error("conversion failed", ex.getMessage());
        return new TypedErrorImpl(ex.getClass().getSimpleName(), ex.getMessage());
    }
}
