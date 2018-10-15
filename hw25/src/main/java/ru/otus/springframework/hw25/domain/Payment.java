package ru.otus.springframework.hw25.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Payment {
    private BigDecimal amount;
    private long merchantId;
}
