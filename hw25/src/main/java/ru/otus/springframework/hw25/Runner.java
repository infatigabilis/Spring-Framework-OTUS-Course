package ru.otus.springframework.hw25;

import com.google.common.util.concurrent.Uninterruptibles;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;
import ru.otus.springframework.hw25.domain.Payment;
import ru.otus.springframework.hw25.domain.Payout;
import ru.otus.springframework.hw25.integration.gateway.OperationGateway;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.concurrent.Executors;

import static java.util.concurrent.TimeUnit.SECONDS;

@Component
public class Runner {
    private final OperationGateway operationGateway;

    public Runner(OperationGateway operationGateway) {
        this.operationGateway = operationGateway;
    }

    @PostConstruct
    public void run() {
        Executors.newSingleThreadExecutor().submit(() -> {
            while(true) {
                Uninterruptibles.sleepUninterruptibly(3, SECONDS);
                operationGateway.process(new Payment(new BigDecimal(RandomUtils.nextInt(1, 100)), RandomUtils.nextInt(1, 5)));
            }
        });

        Executors.newSingleThreadExecutor().submit(() -> {
            while(true) {
                Uninterruptibles.sleepUninterruptibly(5, SECONDS);
                operationGateway.process(new Payout(new BigDecimal(RandomUtils.nextInt(1, 100)), RandomUtils.nextInt(1, 5)));
            }
        });
    }
}
