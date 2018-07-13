package ru.otus.springframework.hw05.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.springframework.hw05.service.printer.TestPrinter;

@ShellComponent
public class TestCommands {
    private final TestPrinter testPrinter;

    public TestCommands(TestPrinter testPrinter) {
        this.testPrinter = testPrinter;
    }

    @ShellMethod("Start testing program")
    public void start() {
        testPrinter.run();
    }
}
