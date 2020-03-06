package com.github.pawelbialas.testgeneratorapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
(scanBasePackages = "com.github.pawelbialas.testgeneratorapp")
public class TestGeneratorAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestGeneratorAppApplication.class, args);
	}

}
