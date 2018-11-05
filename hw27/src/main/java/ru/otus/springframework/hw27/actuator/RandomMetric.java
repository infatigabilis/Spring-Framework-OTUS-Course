package ru.otus.springframework.hw27.actuator;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RandomMetric {

    private final AtomicInteger value = new AtomicInteger(0);

    public RandomMetric(MeterRegistry registry) {
        registry.gauge("random", Tags.empty(), this.value);

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                () -> value.set(ThreadLocalRandom.current().nextInt()),
                0,
                10,
                TimeUnit.SECONDS
        );
    }
}
