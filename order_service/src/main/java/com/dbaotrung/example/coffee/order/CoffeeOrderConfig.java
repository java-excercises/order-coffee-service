package com.dbaotrung.example.coffee.order;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.dbaotrung.example.coffee.order.repository.*")
@EntityScan("com.dbaotrung.example.coffee.order.entity.*")
@Configuration
public class CoffeeOrderConfig {
}
