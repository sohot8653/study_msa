package com.example.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.example.userservice.error.FeignErrorDecoder;

import feign.Logger;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    @LoadBalanced
    RestTemplate getRestTemplate() {
    	return new RestTemplate();
    }
    
    @Bean
    Logger.Level feignLoggerLevel() {
    	return Logger.Level.FULL;
    }
    
    @Bean
    FeignErrorDecoder getFeignErrorDecoder() {
    	return new FeignErrorDecoder();
    }
}
