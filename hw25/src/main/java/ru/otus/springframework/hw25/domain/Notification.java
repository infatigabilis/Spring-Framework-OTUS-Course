package ru.otus.springframework.hw25.domain;

import lombok.Data;

@Data
public class Notification {
    private final String message;
    private final Merchant receiver;
}
