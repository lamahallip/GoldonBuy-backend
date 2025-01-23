package com.goldonbuy.goldonbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class GoldonBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoldonBackendApplication.class, args);
	}

}
