package ru.otus.springframework.hw25.integration.flow;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PriorityChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;
import ru.otus.springframework.hw25.domain.Notification;
import ru.otus.springframework.hw25.service.NotificationService;

import java.util.Comparator;
import java.util.concurrent.TimeUnit;

@Configuration
public class NotificationFlowConfig {

    @Bean
    public MessageChannel notificationChannel() {
        return new PriorityChannel(
                Comparator.comparingInt(notification -> ((Notification) notification).getReceiver().getPriority().getValue())
        );
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata notificationPoller() {
        return Pollers.fixedRate(100, TimeUnit.MILLISECONDS).get();
    }

    @Bean
    public IntegrationFlow notificationFlow(MessageChannel notificationChannel, NotificationService notificationService) {
        return IntegrationFlows.from(notificationChannel)
                .handle(notificationService, "notify")
                .get();
    }
}
