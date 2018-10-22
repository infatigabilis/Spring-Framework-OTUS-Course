package ru.otus.springframework.hw25.integration.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;
import ru.otus.springframework.hw25.domain.Payment;
import ru.otus.springframework.hw25.domain.Payout;

@MessagingGateway
@Component
public interface OperationGateway {

    @Gateway(requestChannel = "operationFlow.input")
    void process(Payment payment);

    @Gateway(requestChannel = "operationFlow.input")
    void process(Payout payout);
}
