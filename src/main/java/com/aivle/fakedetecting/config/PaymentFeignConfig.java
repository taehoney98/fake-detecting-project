package com.aivle.fakedetecting.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentFeignConfig {
    @Value("${payment.api.key}")
    private String secretApiKey;
    @Bean
    public RequestInterceptor paymentFeignRequestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Authorization", "PortOne " + secretApiKey);
        };
    }
}
