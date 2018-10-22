package ru.otus.springframework.hw25.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class Merchant {
    private long id;
    private String name;
    private Priority priority;
    private List<NotificationChannel> notificationChannels;
    private BigDecimal balance;

    private String phone;
    private String email;
    private String callbackHost;

    @AllArgsConstructor
    public enum Priority {
        COMMON(1),
        IMPORTANT(5),
        VIP(15);

        @Getter
        private final int value;
    }

    public enum NotificationChannel {
        EMAIL,
        SMS,
        SERVER_TO_SERVER;
    }
}
