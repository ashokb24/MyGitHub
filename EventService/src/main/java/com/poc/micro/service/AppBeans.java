package com.poc.micro.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppBeans {

	@Bean
	public EventAPIProducer initProducer() {
		return new EventAPIProducer();
	}

	@Bean
	public EventAPIConsumer initConsumer() {
		return new EventAPIConsumer();
	}
}
