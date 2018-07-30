package ru.otus.springframework.hw08;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class Hw06Application {

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(Hw06Application.class, args);
	}
}
