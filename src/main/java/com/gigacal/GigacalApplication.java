package com.gigacal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class GigacalApplication {

	public static void main(String[] args) {
		SpringApplication.run(GigacalApplication.class, args);
	}

}
