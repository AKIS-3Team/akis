package com.example.akis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AkisApplication {

	public static void main(String[] args) {
		SpringApplication.run(AkisApplication.class, args);
	}

}
