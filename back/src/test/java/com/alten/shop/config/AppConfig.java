package com.alten.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.javafaker.Faker;

@Configuration
public class AppConfig {

	@Bean
	Faker faker() {
		return new Faker();
	}
}
