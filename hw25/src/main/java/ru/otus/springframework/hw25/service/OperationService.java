package ru.otus.springframework.hw25.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.springframework.hw25.domain.Merchant;
import ru.otus.springframework.hw25.domain.Notification;
import ru.otus.springframework.hw25.domain.Payment;
import ru.otus.springframework.hw25.domain.Payout;
import ru.otus.springframework.hw25.integration.gateway.NotificationGateway;
import ru.otus.springframework.hw25.repository.MerchantRepository;

import java.math.BigDecimal;

@Service
public class OperationService {
    private final MerchantRepository merchantRepository;
    private final NotificationGateway notificationGateway;

    @Value("${app.notification.balance-notification-threshold}")
    private BigDecimal balanceNotificationThreshold;

    public OperationService(MerchantRepository merchantRepository, NotificationGateway notificationGateway) {
        this.merchantRepository = merchantRepository;
        this.notificationGateway = notificationGateway;
    }

    public void payIn(Payment payment) {
        final Merchant merchant = merchantRepository.getById(payment.getMerchantId());
        merchant.setBalance(merchant.getBalance().add(payment.getAmount()));

        notificationGateway.notify(new Notification("Payment: $" + payment.getAmount(), merchant));
    }

    public void payOut(Payout payout) {
        final Merchant merchant = merchantRepository.getById(payout.getMerchantId());
        merchant.setBalance(merchant.getBalance().subtract(payout.getAmount()));

        notificationGateway.notify(new Notification("Payout: $" + payout.getAmount(), merchant));

        if (merchant.getBalance().compareTo(balanceNotificationThreshold) <= 0) {
            notificationGateway.notify(new Notification("Your balance too low: $" + merchant.getBalance(), merchant));
        }
    }
}
