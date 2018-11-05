package ru.otus.springframework.hw27.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class DinnerHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        if (isDinnerTime()) {
            return Health
                    .down()
                    .withDetail("message", "Microservices walk through hyperlinks like humans, therefore they need to take dinner like humans!")
                    .build();
        } else {
            return Health.up().build();
        }
    }

    private boolean isDinnerTime() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        return currentHour >= 13 && currentHour <= 14;
    }
}
