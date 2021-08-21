package com.auth.authgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class AuthGatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthGatewayApplication.class, args);
	}
}

