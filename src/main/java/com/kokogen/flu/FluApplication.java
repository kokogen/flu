package com.kokogen.flu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FluApplication {
	public static Logger logger = LoggerFactory.getLogger(FluApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(FluApplication.class, args);
	}
}
