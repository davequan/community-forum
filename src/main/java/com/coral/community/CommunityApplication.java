package com.coral.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.coral.community")
public class CommunityApplication {
	public static void main(String[] args) {

		SpringApplication.run(CommunityApplication.class, args);
	}

}
