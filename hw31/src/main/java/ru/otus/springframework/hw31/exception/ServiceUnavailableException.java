package ru.otus.springframework.hw31.exception;

import lombok.Getter;

import java.text.MessageFormat;

public class ServiceUnavailableException extends RuntimeException {

    @Getter
    private final String serviceName;

    public ServiceUnavailableException(String serviceName, Throwable cause) {
        super(cause);
        this.serviceName = serviceName;
    }

    @Override
    public String getMessage() {
        return MessageFormat.format("Service ''{0}'' is unavailable", serviceName);
    }
}
