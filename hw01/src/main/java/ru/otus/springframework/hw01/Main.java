package ru.otus.springframework.hw01;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        final TestPrinter printer = context.getBean("testPrinter", TestPrinter.class);

        printer.run();
    }
}
