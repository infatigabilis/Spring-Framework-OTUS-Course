package ru.otus.springframework.hw25.service;

import org.springframework.stereotype.Service;
import ru.otus.springframework.hw25.domain.Notification;
import ru.otus.springframework.hw25.service.sender.EmailSender;
import ru.otus.springframework.hw25.service.sender.SmsSender;
import ru.otus.springframework.hw25.service.sender.StsSender;

import static ru.otus.springframework.hw25.domain.Merchant.NotificationChannel.*;

@Service
public class NotificationService {
    private final EmailSender emailSender;
    private final SmsSender smsSender;
    private final StsSender stsSender;

    public NotificationService(EmailSender emailSender, SmsSender smsSender, StsSender stsSender) {
        this.emailSender = emailSender;
        this.smsSender = smsSender;
        this.stsSender = stsSender;
    }

    public void notify(Notification notification) {
        if (notification.getReceiver().getNotificationChannels().contains(EMAIL)) {
            emailSender.send(notification.getMessage(), notification.getReceiver().getEmail());
        }

        if (notification.getReceiver().getNotificationChannels().contains(SMS)) {
            smsSender.send(notification.getMessage(), notification.getReceiver().getPhone());
        }

        if (notification.getReceiver().getNotificationChannels().contains(SERVER_TO_SERVER)) {
            stsSender.send(notification.getMessage(), notification.getReceiver().getCallbackHost());
        }
    }
}
