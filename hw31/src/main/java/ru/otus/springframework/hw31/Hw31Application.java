package ru.otus.springframework.hw31;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class Hw31Application {

	public static void main(String[] args) {
		SpringApplication.run(Hw31Application.class, args);
	}
}
