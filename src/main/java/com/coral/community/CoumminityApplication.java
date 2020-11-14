package com.coral.community;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class CoumminityApplication {
	private Logger logger = LoggerFactory.getLogger(getClass());
	public static void main(String[] args) {

		SpringApplication.run(CoumminityApplication.class, args);
	}
	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Logger logger(InjectionPoint injectionPoint){
		logger.debug("debug infor");
		return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
	}
	
}
