package ru.otus.springframework.hw02;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.io.IOException;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        final TestPrinter printer = context.getBean(TestPrinter.class);

        printer.run();
    }

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/i18n/bundle");
        ms.setDefaultEncoding("UTF-8");

        return ms;
    }
}
