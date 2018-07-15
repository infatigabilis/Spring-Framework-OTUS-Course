package ru.otus.springframework.hw05;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.springframework.hw05.service.printer.TestPrinter;

@SpringBootApplication
public class Hw5Application {

	public static void main(String[] args) {
		SpringApplication.run(Hw5Application.class);
	}
}
