package com.aivle.fakedetecting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FakedetectingApplication {

	public static void main(String[] args) {
		SpringApplication.run(FakedetectingApplication.class, args);
	}

}
