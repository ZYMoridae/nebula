package com.jz.nebula;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.jz.nebula.service.payment.StripeProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

// Remove SpringBootServletInitializer when change to jar build
@SpringBootApplication
@EnableConfigurationProperties(StripeProperties.class)
@EnableScheduling
@ComponentScan(basePackages = {"com.jz.nebula.service", "com.jz.nebula.config", "com.jz.nebula.component", "com.jz.nebula"})
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
