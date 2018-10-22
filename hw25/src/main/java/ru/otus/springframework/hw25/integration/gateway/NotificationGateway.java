package ru.otus.springframework.hw25.integration.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.otus.springframework.hw25.domain.Notification;

@MessagingGateway
@Component
public interface NotificationGateway {

    @Async
    @Gateway(requestChannel = "notificationChannel")
    void notify(Notification notification);
}
