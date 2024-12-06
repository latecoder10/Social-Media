package com.tap.social;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SocialPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialPlatformApplication.class, args);
	}

}
