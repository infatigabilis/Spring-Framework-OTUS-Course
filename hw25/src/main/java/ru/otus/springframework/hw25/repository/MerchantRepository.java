package ru.otus.springframework.hw25.repository;

import org.springframework.stereotype.Service;
import ru.otus.springframework.hw25.domain.Merchant;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.otus.springframework.hw25.domain.Merchant.NotificationChannel.EMAIL;
import static ru.otus.springframework.hw25.domain.Merchant.NotificationChannel.SMS;
import static ru.otus.springframework.hw25.domain.Merchant.NotificationChannel.SERVER_TO_SERVER;
import static ru.otus.springframework.hw25.domain.Merchant.Priority.COMMON;

@Service
public class MerchantRepository {
    private final List<Merchant> merchants = new ArrayList<>();

    @PostConstruct
    public void seed() {
        merchants.add(new Merchant(1, "merchant 1", COMMON, Arrays.asList(EMAIL), new BigDecimal("1000"), null, "merchant1@mail.com", null));
        merchants.add(new Merchant(2, "merchant 2", COMMON, Arrays.asList(EMAIL, SMS), new BigDecimal("0"), "+70001112233", "merchant2@mail.com", null));
        merchants.add(new Merchant(3, "merchant 3", COMMON, Arrays.asList(SERVER_TO_SERVER), new BigDecimal("12.34"), null, "merchant3@mail.com", "otus.ru"));
        merchants.add(new Merchant(4, "merchant 4", COMMON, Arrays.asList(EMAIL, SMS, SERVER_TO_SERVER), new BigDecimal("0"), "+10001112233", "merchant4@mail.com", "google.com"));
    }

    public List<Merchant> getAll() {
        return merchants;
    }

    public Merchant getById(long id) {
        return merchants.stream().filter(merchant -> merchant.getId() == id).findAny().get();
    }
}
