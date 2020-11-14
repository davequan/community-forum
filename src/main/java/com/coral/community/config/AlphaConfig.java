package com.coral.community.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.text.SimpleDateFormat;

@Configuration
public class AlphaConfig {
    @Bean
    public SimpleDateFormat simpleDateFormat(){
        return  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //this method's return type will be in bean container
    }

}
