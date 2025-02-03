package com.aivle.fakedetecting.config;

import feign.Feign;
import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Request.Options options() {
        return new Request.Options(300000, 300000);
    }

    @Bean
    public Feign.Builder feignBuilder() {
        return Feign.builder().options(options());
    }
}
