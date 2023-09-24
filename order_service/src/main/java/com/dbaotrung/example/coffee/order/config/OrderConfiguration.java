package com.dbaotrung.example.coffee.order.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories({"com.dbaotrung.example.coffee.order.repository"})
public class OrderConfiguration {
}
