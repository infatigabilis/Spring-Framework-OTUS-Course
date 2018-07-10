package ru.otus.springframework.hw04;

import org.springframework.stereotype.Component;
import ru.otus.springframework.hw04.service.printer.TestPrinter;

import javax.annotation.PostConstruct;

@Component
public class Runner {
    private final TestPrinter testPrinter;

    public Runner(TestPrinter testPrinter) {
        this.testPrinter = testPrinter;
    }

    @PostConstruct
    public void run() {
        testPrinter.run();
    }
}
