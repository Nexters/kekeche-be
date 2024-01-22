package com.nexters.kekechebe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KekecheBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(KekecheBeApplication.class, args);
	}

}
