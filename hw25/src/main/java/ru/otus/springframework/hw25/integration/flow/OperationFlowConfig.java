package ru.otus.springframework.hw25.integration.flow;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import ru.otus.springframework.hw25.domain.Payment;
import ru.otus.springframework.hw25.domain.Payout;
import ru.otus.springframework.hw25.service.OperationService;

@Configuration
public class OperationFlowConfig {

    @Bean
    public IntegrationFlow operationFlow(OperationService operationService) {
        return f -> f
                .<Object, Class<?>>route(Object::getClass, m -> m
                        .subFlowMapping(Payment.class, sf -> sf.handle(operationService, "payIn"))
                        .subFlowMapping(Payout.class, sf -> sf.handle(operationService, "payOut"))
                );
    }
}
