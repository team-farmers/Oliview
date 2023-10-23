package com.farmers.oliview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class OliviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(OliviewApplication.class, args);
	}

}
