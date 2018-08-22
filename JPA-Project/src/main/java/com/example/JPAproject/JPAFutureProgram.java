package com.example.JPAproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JPAFutureProgram {
	public static void main(String[] args) {
		SpringApplication.run(JPAFutureProgram.class, args);
	}
}
