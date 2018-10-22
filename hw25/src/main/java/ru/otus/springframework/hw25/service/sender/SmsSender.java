package ru.otus.springframework.hw25.service.sender;

import com.google.common.util.concurrent.Uninterruptibles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static java.util.concurrent.TimeUnit.SECONDS;

@Service
public class SmsSender {
    private static final Logger logger = LoggerFactory.getLogger(SmsSender.class);

    @Async
    public void send(String message, String phone) {
        Uninterruptibles.sleepUninterruptibly(1, SECONDS);
        logger.info("Message '{}' was sent to {}", message, phone);
    }
}
