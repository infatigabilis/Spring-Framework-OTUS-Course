package ru.otus.springframework.hw04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.springframework.hw04.service.printer.TestPrinter;

@SpringBootApplication
public class Hw4Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Hw4Application.class, args);
		context.getBean(TestPrinter.class).run();
	}

	@Bean
	public MessageSource messageSource() {
		final ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
		ms.setBasename("/i18n/bundle");
		ms.setDefaultEncoding("UTF-8");

		return ms;
	}
}
