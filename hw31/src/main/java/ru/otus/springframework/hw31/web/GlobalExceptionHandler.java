package ru.otus.springframework.hw31.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.otus.springframework.hw31.exception.ServiceUnavailableException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final String ERROR_503 = "error/503";

    @ExceptionHandler(ServiceUnavailableException.class)
    public String serviceUnavailableException(ServiceUnavailableException e) {
        logger.error("", e);
        return ERROR_503;
    }
}
