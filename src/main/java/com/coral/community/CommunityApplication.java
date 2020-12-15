package com.coral.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = "com.coral.community")
public class CommunityApplication {
	@PostConstruct
	public  void init(){
		// resolve netty Start conflict problem
		//Netty4Utils.setAvailableProcessors(
		System.setProperty("es.set.netty.runtime.available.processors","false");
	}
	public static void main(String[] args) {

		SpringApplication.run(CommunityApplication.class, args);
	}

}
